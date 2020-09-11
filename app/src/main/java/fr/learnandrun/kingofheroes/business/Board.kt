package fr.learnandrun.kingofheroes.business

import android.util.Log
import androidx.lifecycle.viewModelScope
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

    val playerInsideCity: Player? = null

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
        var playersCompetitor = this.players.toList()
        do {
            //Generate each players slaps number
            val diceDraws = playersCompetitor
                .map { player ->
                    player to player
                        .rollDices(DICE_AMOUNT)
                        .filter { it == DiceFace.SLAP }
                        .count()
                }

            val firstPair = diceDraws
                .reduce { maxSlaps, element ->
                    if (maxSlaps.second < element.second) element else maxSlaps
                }

            //Eliminate competitors with lower number of slaps (than min)
            playersCompetitor = diceDraws
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
        TODO(player.toString())
    }

    /* Companion Object */
    companion object {
        const val DICE_AMOUNT = 6
    }

}