package fr.learnandrun.kingofheroes.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import fr.learnandrun.kingofheroes.R
import fr.learnandrun.kingofheroes.business.*
import fr.learnandrun.kingofheroes.business.dice.DiceFace
import kotlinx.coroutines.delay
import java.lang.IllegalStateException

class BoardViewModel(application: Application) : AndroidViewModel(application) {

    var isInit = false
    lateinit var board: Board
    lateinit var players: List<Player>

    val throwDiceClickNameLiveData: MutableLiveData<String> =
        MutableLiveData(application.getString(R.string.btn_throw))

    private var isPaused = MutableLiveData(false)

    var showRollDicesInterfaceLambda: (isUser: Boolean) -> Unit = {
        throw IllegalStateException("This function must be overridden by the board fragment")
    }
    var showBoardInterfaceLambda: () -> Unit = {
        throw IllegalStateException("This function must be overridden by the dice fragment")
    }
    var showRollDicesAnimationLambda: (dices: List<DiceFace?>) -> Unit = {
        throw IllegalStateException("This function must be overridden by the dice fragment")
    }

    fun initGame(selectedHero: Hero) {
        val heroes = Hero.values().toMutableSet()
        heroes.remove(selectedHero)

        // listOf take varargs and the operator "*" convert type array in multiple args
        // The result of this is a List<Player> with a user and 3 IAs
        players = listOf(User(selectedHero),
            *(1..3).map {
                IA(heroes.random().also { heroes.remove(it) })
            }.toTypedArray()
        )

        board = Board(this, players)
        isInit = true
    }

    fun startGame() {
        board.startGame()
    }

    fun resetGame() {
        isInit = false
        isPaused = MutableLiveData(false)
    }

    fun canSelectDices() = board.canSelectDices

    suspend fun gameHasStarted() = waitIfPauseable {
        //TODO play animation: Game has started
    }
    suspend fun gameHasEnded() = waitIfPauseable {
        //TODO play animation: Game has ended
    }

    suspend fun showRollDicesInterface(player: Player) = waitIfPauseable {
        showRollDicesInterfaceLambda(player is User)
    }

    suspend fun showRollDiceButton() = waitIfPauseable {
        //TODO: Display the button to roll dice
        board.waitForResume()
        //TODO: And then the button will board.resumeGame()
    }
    suspend fun showRollDicesAnimation(diceFaceResults: List<DiceFace>) = waitIfPauseable {
        // TODO: play animation
        //  maybe wou will need delay(TIME) or board.waitForResume() and board.resumeGame()
        showRollDicesAnimationLambda(diceFaceResults)
        delay(1500)
    }

    suspend fun showBoardInterface() = waitIfPauseable {
        val nullDices: List<DiceFace?> = List(Board.DICE_AMOUNT) { null }
        showRollDicesAnimationLambda(nullDices)
        showBoardInterfaceLambda()
    }

    suspend fun playerHasWon(player: Player) = waitIfPauseable {
        //TODO play animation of a player who has won
    }

    suspend fun playerTurnStart(player: Player) = waitIfPauseable {
        delay(5000)
    }

    suspend fun playerTurnEnd(player: Player) = waitIfPauseable {
        //TODO play animation: Player turn end
    }

    suspend fun startDefineFirstPlayer() = waitIfPauseable {
        //TODO play animation: Start defining first player
        delay(2000)
    }

    suspend fun loopDefineFirstPlayer() = waitIfPauseable {
        //TODO play animation: Loop in defineFirstPlayer
        // because several players has the same lowest number of slaps
    }

    suspend fun firstPlayerDefined(firstPlayer: Player) = waitIfPauseable {
        //TODO play animation: First player defined
    }

    suspend fun defineFirstPlayerTurn() = waitIfPauseable {
        delay(2500)
    }

    private suspend fun waitIfPauseable(function: suspend () -> Unit) {
        if (isPaused.value == true)
            board.waitForResume()
        function()
    }
}