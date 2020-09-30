package fr.learnandrun.kingofheroes.business.dice

class DicePool {

    val dices = List(DICE_AMOUNT) { Dice() }

    fun getDice(index: Int): Dice {
        check(index in 0..DICE_AMOUNT)
        return dices[index]
    }

    fun reset() = dices.forEach {
        it.apply {
            isSelected = false
            diceFace = null
        }
    }

    fun clearDices() {
        dices.forEach { it.clear() }
    }

    companion object {
        const val DICE_AMOUNT = 6
    }

}