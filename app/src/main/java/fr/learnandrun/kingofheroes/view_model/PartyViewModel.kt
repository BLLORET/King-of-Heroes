package fr.learnandrun.kingofheroes.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import fr.learnandrun.kingofheroes.business.Board
import fr.learnandrun.kingofheroes.business.Player
import fr.learnandrun.kingofheroes.business.TurnLoop
import fr.learnandrun.kingofheroes.business.User
import fr.learnandrun.kingofheroes.business.shop.Shop
import fr.learnandrun.kingofheroes.business.dice.DiceFace
import fr.learnandrun.kingofheroes.business.dice.DicePool
import fr.learnandrun.kingofheroes.tools.delegate.DelegateLiveData
import fr.learnandrun.kingofheroes.tools.event.LiveEvent
import fr.learnandrun.kingofheroes.ui.board_screen.BoardFragmentDirections
import fr.learnandrun.kingofheroes.ui.dice_screen.DiceFragmentDirections
import fr.learnandrun.kingofheroes.ui.select_fighter_screen.SelectFighterFragmentDirections
import fr.learnandrun.kingofheroes.ui.shop_screen.ShopFragmentDirections
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.random.Random

class PartyViewModel: ViewModel() {

    /* Coroutine logic */
    private var job: Job? = null
    private var continuation: Continuation<Unit>? = null
    private var leaveCityContinuation: Continuation<Boolean>? = null
    private var fragmentLoadedContinuation: Continuation<Unit>? = null

    fun resumeGame() = viewModelScope.launch {
        continuation?.let {
            it.resume(Unit)
            continuation = null
        } ?: Log.d("Error", "There is nothing to continue! Continuation is null")
    }
    suspend fun waitForResume() {
        if (continuation == null) {
            suspendCoroutine<Unit> { cont ->
                continuation = cont
            }
        }
    }

    fun resumeLeaveCity(leaveCity: Boolean) = viewModelScope.launch {
        leaveCityContinuation?.let {
            it.resume(leaveCity)
            leaveCityContinuation = null
        } ?: Log.d("Error", "There is nothing to continue! leaveCityContinuation is null")
    }
    suspend fun waitForLeaveCityResponse(): Boolean {
        if (leaveCityContinuation == null) {
            return suspendCoroutine { cont ->
                leaveCityContinuation = cont
            }
        }
        throw IllegalStateException(
            "You have already called this function without calling resumeLeaveCity")
    }

    fun fragmentLoaded() = viewModelScope.launch {
        fragmentLoadedContinuation?.let {
            it.resume(Unit)
            fragmentLoadedContinuation = null
        }
    }
    suspend fun waitForFragmentLoaded() {
        if (fragmentLoadedContinuation == null) {
            suspendCoroutine<Unit> { cont ->
                fragmentLoadedContinuation = cont
            }
        }
    }

    lateinit var board: Board
    val dicePool = DicePool()
    val shop = Shop()
    private lateinit var turnLoop: TurnLoop

    val diceRollRemainingLive = DelegateLiveData(1)
    var diceRollRemaining: Int by diceRollRemainingLive

    val canSelectDicesLive = DelegateLiveData(false)
    private var canSelectDices by canSelectDicesLive

    val canSelectCardLive = DelegateLiveData(false)
    private var canSelectCard by canSelectCardLive

    val currentPlayerLive = DelegateLiveData<Player>(null)
    var currentPlayer by currentPlayerLive

    val navigateEvent = LiveEvent<NavDirections>()
    val proposeToLeaveTheCityEvent = LiveEvent<Unit>()

    suspend fun navigate(navDirections: NavDirections) {
        navigateEvent.trigger(navDirections)
        waitForFragmentLoaded()
        delay(1)
    }

    fun initParty(players: List<Player>) {
        board = Board(players)
        board.playerInsideCity = null
        dicePool.reset()
        shop.initShop()
        job?.cancel()
        job = viewModelScope.launch {
            navigate(SelectFighterFragmentDirections.actionSelectFighterFragmentToBoardFragment())
            defineFirstPlayerLoop()
            gameLoop()
        }
    }

    private suspend fun defineFirstPlayerLoop() {
        var playersCompetitor = board.players.toList()
        do {
            //Generate each players slaps number
            val dicesDraws = playersCompetitor.map { player ->

                currentPlayer = player
                delay(DELAY_TIME)

                // reset dices
                dicePool.clearDices()

                diceRollRemaining = 1
                // display the dices interface
                navigate(BoardFragmentDirections.actionBoardFragmentToDiceFragment())

                // wait for the player to roll its dices
                if (currentPlayer is User)
                    waitForResume()
                else
                    delay(DELAY_TIME)

                diceRollRemaining = 0
                // roll dices
                dicePool.dices.forEach { it.roll() }

                delay(DELAY_TIME)

                // display board interface
                navigate(DiceFragmentDirections.actionDiceFragmentToBoardFragment())

                player to dicePool.dices.filter { it.diceFace == DiceFace.SLAP }.count()
            }

            val firstPair = dicesDraws
                .reduce { maxSlaps, element ->
                    if (maxSlaps.second < element.second) element else maxSlaps
                }

            //Eliminate competitors with lower number of slaps (than min)
            playersCompetitor = dicesDraws
                .filter { it.second == firstPair.second }
                .map { it.first }

        } while (playersCompetitor.size != 1)

        val firstPlayer = playersCompetitor.first()
        turnLoop = TurnLoop(board.players)
        turnLoop.setFirstPlayer(firstPlayer)
    }

    private suspend fun gameLoop() {
        for (player in turnLoop) {
            currentPlayer = player
            delay(DELAY_TIME)

            playTurn()

            if (playerHasWon(player)) {
                currentPlayer = player
                delay(DELAY_TIME)
                navigate(BoardFragmentDirections.actionBoardFragmentToFinalScreenFragment())
                break
            }
        }
    }

    private fun playerHasWon(player: Player): Boolean =
        !player.isDead() &&
                (player.hasEnoughPointsToWin() ||
                        board.players.filter { it != player }.all { it.isDead() })

    private suspend fun playTurn() {
        if (board.playerInsideCity == currentPlayer)
            currentPlayer.victoryPoints += 2

        if (!playerHasWon(currentPlayer)) {
            rollDices()
            resolveDices()

            if (!playerHasWon(currentPlayer)) {
                if (board.playerInsideCity == null) {
                    board.playerInsideCity = currentPlayer
                    currentPlayer.victoryPoints++
                    delay(DELAY_TIME)
                }

                if (!playerHasWon(currentPlayer)) {
                    openShop()
                }
            }
        }
    }

    private suspend fun rollDices() {
        // reset dices
        dicePool.clearDices()
        diceRollRemaining = 3

        // display the dices interface
        navigate(BoardFragmentDirections.actionBoardFragmentToDiceFragment())

        // wait for the player to roll its dices
        if (currentPlayer is User)
            waitForResume()
        else
            delay(DELAY_TIME)

        diceRollRemaining--

        do {
            // roll dices for all null dices
            dicePool.dices.filter { it.isSelected || it.diceFace == null }.forEach { it.roll() }

            // will set at null the dices to re roll
            if (currentPlayer is User) {
                canSelectDices = true
                waitForResume()
                canSelectDices = false
            }
            else {
                dicePool.dices.forEach {
                    if (Random.nextInt(0, 4) == 0) {
                        delay(DELAY_TIME)
                        it.isSelected = true
                    }
                }
                delay(DELAY_TIME)
            }

            diceRollRemaining--

        } while (diceRollRemaining > 0 && dicePool.dices.any { it.isSelected })

        if (dicePool.dices.any { it.isSelected }) {
            // roll dices for all null dices
            dicePool.dices.filter { it.isSelected }.forEach { it.roll() }

            if (currentPlayer is User)
                waitForResume()
            else
                delay(DELAY_TIME)
        }

        navigate(DiceFragmentDirections.actionDiceFragmentToBoardFragment())
    }

    private suspend fun resolveDices() {
        slapPlayers()

        if (currentPlayer != board.playerInsideCity)
            currentPlayer.health += countDice(DiceFace.HEART)

        currentPlayer.energy += countDice(DiceFace.LIGHTNING)

        fun calculatePoints(diceFace: DiceFace, points: Int) =
            countDice(diceFace).takeIf { it >= 3 }?.let {
                currentPlayer.victoryPoints += points + it - 3
            }

        calculatePoints(DiceFace.ONE, 1)
        calculatePoints(DiceFace.TWO, 2)
        calculatePoints(DiceFace.THREE, 3)
    }

    private fun countDice(diceFace: DiceFace) =
        dicePool.dices.filter { it.diceFace == diceFace }.count()

    private suspend fun slapPlayers() {
        delay(DELAY_TIME)

        val nbSlaps = countDice(DiceFace.SLAP)
        // Check all conditions to slap
        if (nbSlaps <= 0) return
        if (turnLoop.isFirstTurn()) return
        val playerInsideCity = board.playerInsideCity ?: return

        if (playerInsideCity == currentPlayer)
            board.players.filter { it != currentPlayer }.forEach { it.health -= nbSlaps }
        else {
            playerInsideCity.health -= nbSlaps
            if (playerInsideCity.isDead()) {
                board.playerInsideCity = null
                return
            }
            if (playerInsideCity is User) {
                proposeToLeaveTheCityEvent.trigger(Unit)
                if (waitForLeaveCityResponse()) {
                    board.playerInsideCity = null
                    delay(DELAY_TIME)
                }
            }
            else {
                // If the IA wants to leave the city (currently driven by random)
                if (Random.nextBoolean()) {
                    board.playerInsideCity = null
                    delay(DELAY_TIME)
                }
            }
        }
    }

    private suspend fun openShop() {
        //open shop and get random cards
        shop.openShop()

        // show shop interface
        delay(DELAY_TIME)
        navigate(BoardFragmentDirections.actionBoardFragmentToShopFragment())

        if (currentPlayer is User) {
            canSelectCard = true
            waitForResume()
            canSelectCard = false
        }
        else {
            shop.cards.forEachIndexed { index, card ->
                if (card.card.price <= currentPlayer.energy) {
                    if (Random.nextBoolean()) {
                        delay(DELAY_TIME)
                        shop.selectCard(index)
                    }
                }
            }
            delay(DELAY_TIME)
        }

        // navigate back to board fragment
        navigate(ShopFragmentDirections.actionShopFragmentToBoardFragment())

        // buy the selected card
        currentPlayer.energy -= shop.currentSelectedCard?.card?.price ?: 0
        // apply selected card effect
        shop.currentSelectedCard?.card?.effect?.invoke(currentPlayer,
            board.players.filter { player -> player != currentPlayer },
            currentPlayer == board.playerInsideCity)
        if (board.playerInsideCity?.isDead() == true) {
            board.playerInsideCity = null
            delay(DELAY_TIME)
        }

        // close the shop and redraw selected cards
        shop.closeShop()
    }

    companion object {
        const val DELAY_TIME: Long = 2000
    }
}