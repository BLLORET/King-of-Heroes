package fr.learnandrun.kingofheroes.business

import fr.learnandrun.kingofheroes.business.dice.DiceFace
import kotlinx.coroutines.delay
import kotlin.random.Random

class IA(
    hero: Hero
) : Player(hero) {

    override suspend fun waitForRollClick(board: Board) {
        delay(waitingTime)
    }

    override suspend fun waitForReRollOrPassClick(board: Board, dices: MutableList<DiceFace?>) {
        dices.forEachIndexed { index, _ ->
            if (Random.nextInt(0, 4) == 0)
                dices[index] = null
        }
        delay(waitingTime)
    }

    override suspend fun waitForEndRollClick(board: Board) {
        delay(waitingTime)
    }

    override suspend fun decreaseHealth(board: Board, value: Int) {
        super.decreaseHealth(board, value)
        // If the IA wants to leave the city (currently driven by random)
        if (board.playerInsideCity == this && Random.nextBoolean())
            board.playerInsideCity = null
    }

    companion object {
        const val waitingTime: Long = 1000
    }
}