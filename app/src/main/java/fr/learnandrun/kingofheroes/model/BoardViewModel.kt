package fr.learnandrun.kingofheroes.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import fr.learnandrun.kingofheroes.business.*
import fr.learnandrun.kingofheroes.business.dice.DiceFace

class BoardViewModel(application: Application) : AndroidViewModel(application) {

    var isInit = false
    lateinit var board: Board
    lateinit var players: List<Player>

    private var isPaused = MutableLiveData(false)

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

    suspend fun gameHasStarted() = waitIfPauseable {
        //TODO play animation: Game has started
    }
    suspend fun gameHasEnded() = waitIfPauseable {
        //TODO play animation: Game has ended
    }

    suspend fun showRollDicesInterface(player: Player) = waitIfPauseable {
        //TODO: display interface over the game board interface (empty dices)
        // IF ITS A USER => Show Button
        // ELSE (ITS AN AI) => Do Not Show Button
        // VVVVVVVVV

        when(player) {
            is IA -> println("IA")
            is User -> println("User")
        }
    }

    suspend fun showRollDiceButton() = waitIfPauseable {
        //TODO: Display the button to roll dice
        board.waitForResume()
        //TODO: And then the button will board.resumeGame()
    }
    suspend fun showRollDicesAnimation(diceFaceResults: List<DiceFace>) = waitIfPauseable {
        // TODO: play animation
        //  maybe wou will need delay(TIME) or board.waitForResume() and board.resumeGame()
    }

    suspend fun playerHasWon(player: Player) = waitIfPauseable {
        //TODO play animation of a player who has won
    }

    suspend fun playerTurnStart(player: Player) = waitIfPauseable {
        //TODO play animation: Player turn start
    }

    suspend fun playerTurnEnd(player: Player) = waitIfPauseable {
        //TODO play animation: Player turn end
    }

    suspend fun startDefineFirstPlayer() = waitIfPauseable {
        //TODO play animation: Start defining first player
    }

    suspend fun loopDefineFirstPlayer() = waitIfPauseable {
        //TODO play animation: Loop in defineFirstPlayer
        // because several players has the same lowest number of slaps
    }

    suspend fun firstPlayerDefined(firstPlayer: Player) = waitIfPauseable {
        //TODO play animation: First player defined
    }

    private suspend fun waitIfPauseable(function: suspend () -> Unit) {
        if (isPaused.value == true)
            board.waitForResume()
        function()
    }


}