package fr.learnandrun.kingofheroes.business

import fr.learnandrun.kingofheroes.business.dice.DiceFace
import kotlinx.coroutines.delay
import kotlin.random.Random

class IA(
    board: Board,
    hero: Hero
) : Player(board, hero) {

    override suspend fun waitForRollClick() {
        delay(1500)
    }

    override suspend fun waitForReRollOrPassClick(dices: MutableList<DiceFace?>) {
        dices.forEachIndexed { index, diceFace ->
            if (Random.nextInt(0, 4) == 0)
                dices[index] = null
        }
        delay(1500)
    }

    override suspend fun waitForEndRollClick() {
        delay(1500)
    }

    override suspend fun decreaseHealth(value: Int) {
        //TODO ICI J'SÉ PO :( HELP ME GINET :( :'(
    }
}