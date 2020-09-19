package fr.learnandrun.kingofheroes.business

import android.util.Log
import androidx.lifecycle.viewModelScope
import fr.learnandrun.kingofheroes.business.dice.Dice
import fr.learnandrun.kingofheroes.business.dice.DiceFace
import fr.learnandrun.kingofheroes.model.BoardViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class Board(
    val boardViewModel: BoardViewModel,
    val players: List<Player>
) {

    var playerInsideCity: Player? = null

    private var gameStarted = false
    private val turnLoop = TurnLoop(players)

    /* Coroutine logic */
    private var job: Job? = null
    private var continuation: Continuation<Unit>? = null

    private fun isGameWaiting() = continuation != null

    fun resumeGame() {
        continuation?.let {
            it.resume(Unit)
            continuation = null
        } ?: Log.d("Error", "There is nothing to continue! Continuation is null")
    }

    suspend fun waitForResume() {
        if (!isGameWaiting()) {
            suspendCoroutine<Unit> { cont ->
                continuation = cont
            }
        }
    }

    fun startGame() {
        job?.cancel()
        job = boardViewModel.viewModelScope.launch {
            defineFirstPlayerLoop()
            gameLoop()
        }
    }

    private suspend fun defineFirstPlayerLoop() {
        boardViewModel.startDefineFirstPlayer()
        var playersCompetitor = players.toList()
        do {
            //Generate each players slaps number
            val dicesDraws = playersCompetitor.map { player ->
                // display the dices interface
                boardViewModel.showRollDicesInterface(player)

                // wait for the player to roll its dices
                player.waitForRollClick(this)

                // roll dices
                val dicesFaceResult = generateSequence { Dice.roll() }
                    .take(DICE_AMOUNT)
                    .toList()

                // display result
                boardViewModel.showRollDicesAnimation(dicesFaceResult)

                player to dicesFaceResult.filter { it == DiceFace.SLAP }.count()
            }

            val firstPair = dicesDraws
                .reduce { maxSlaps, element ->
                    if (maxSlaps.second < element.second) element else maxSlaps
                }

            //Eliminate competitors with lower number of slaps (than min)
            playersCompetitor = dicesDraws
                .filter { it.second == firstPair.second }
                .map { it.first }

            // We call loopDefineFirstPlayer in the while only if we have to re-enter in the loop
        } while (playersCompetitor.size != 1 && boardViewModel.loopDefineFirstPlayer().let { true })

        val firstPlayer = playersCompetitor.first()
        turnLoop.setFirstPlayer(firstPlayer)
        boardViewModel.firstPlayerDefined(firstPlayer)
    }

    private suspend fun gameLoop() {
        check(!gameStarted)
        gameStarted = true

        boardViewModel.gameHasStarted()

        for (player in turnLoop) {
            boardViewModel.playerTurnStart(player)

            playTurn(player)

            if (playerHasWon(player)) {
                boardViewModel.playerHasWon(player)
                break
            }
            boardViewModel.playerTurnEnd(player)
        }

        boardViewModel.gameHasEnded()
        gameStarted = false
    }

    private fun playerHasWon(player: Player): Boolean =
        !player.isDead() &&
                (player.hasEnoughPointsToWin() ||
                        players.filter { it != player }.all { it.isDead() })

    private suspend fun playTurn(player: Player) {
        startTurn(player)
        val dices = rollDices(player)
        resolveDices(dices, player)
    }

    private fun startTurn(player: Player) {
        when (playerInsideCity) {
            player -> player.increaseVictoryPoints(2)
            null -> {
                playerInsideCity = player
                player.increaseVictoryPoints()
            }
        }
    }

    private suspend fun rollDices(player: Player): List<DiceFace> {
        var tryRemaining = 3
        val dices: MutableList<DiceFace?> = generateSequence { null }.take(DICE_AMOUNT).toMutableList()

        // display the dices interface
        boardViewModel.showRollDicesInterface(player)

        // wait for the player to roll its dices
        player.waitForRollClick(this)

        do {
            // roll dices for all null dices
            for (index in 0 until dices.size) {
                if (dices[index] == null) dices[index] = Dice.roll()
            }

            // display the dices interface
            boardViewModel.showRollDicesAnimation(dices.filterNotNull())

            // will set at null the dices to re roll
            player.waitForReRollOrPassClick(this, dices)
        } while (--tryRemaining > 1 && dices.filter { it == null }.count() != 0)

        // roll dices for all null dices
        for (index in 0 until dices.size) {
            if (dices[index] == null) dices[index] = Dice.roll()
        }

        // display the dices interface
        boardViewModel.showRollDicesAnimation(dices.filterNotNull())

        // button to end the roll dices phase
        player.waitForEndRollClick(this)

        return dices.filterNotNull()
    }

    private suspend fun resolveDices(dices: List<DiceFace>, player: Player) {
        var numberOfOne = 0
        var numberOfTwo = 0
        var numberOfThree = 0

        for (dice in dices) {
            when (dice) {
                DiceFace.HEART -> if (playerInsideCity != player) player.increaseHealth()
                DiceFace.LIGHTNING -> player.increaseEnergy()
                DiceFace.SLAP -> if (!turnLoop.isFirstTurn()) slapPlayers(player)
                DiceFace.ONE -> numberOfOne++
                DiceFace.TWO -> numberOfTwo++
                DiceFace.THREE -> numberOfThree++
            }
        }

        if (numberOfOne > 3) player.increaseVictoryPoints(1 + (numberOfOne - 3))
        if (numberOfTwo > 3) player.increaseVictoryPoints(2 + (numberOfTwo - 3))
        if (numberOfThree > 3) player.increaseVictoryPoints(3 + (numberOfThree - 3))
    }

    private suspend fun slapPlayers(player: Player) {
        if (playerInsideCity != player)
            playerInsideCity?.decreaseHealth(this)
        else
            players.filter { it != player }.forEach { it.decreaseHealth(this) }
    }

    /* Companion Object */
    companion object {
        const val DICE_AMOUNT = 6
    }

}