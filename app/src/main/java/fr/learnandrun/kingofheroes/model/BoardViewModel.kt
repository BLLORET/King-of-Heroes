package fr.learnandrun.kingofheroes.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.learnandrun.kingofheroes.business.Board
import fr.learnandrun.kingofheroes.business.Player
import fr.learnandrun.kingofheroes.business.dice.DiceFace

class BoardViewModel(
    players: List<Player>
) : ViewModel() {

    val isPaused = MutableLiveData(false)

    val board = Board(this, players)


    suspend fun gameHasStarted() = waitIfPauseable {
        //TODO play animation: Game has started
    }
    suspend fun gameHasEnded() = waitIfPauseable {
        //TODO play animation: Game has ended
    }

    suspend fun showRollDicesInterface() = waitIfPauseable {
        //TODO: display interface over the game board interface (empty dices)
    }
    suspend fun showRollDiceButton() = waitIfPauseable {
        //TODO: Display the button to roll dice
        board.waitForResume()
        //TODO: And then the button will board.resumeGame()
    }
    suspend fun showRollDicesAnimation(diceFaceResults: List<DiceFace?>) = waitIfPauseable {
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