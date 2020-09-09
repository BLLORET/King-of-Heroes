package fr.learnandrun.kingofheroes.business.dice

object Dice {
    fun roll(): DiceFace = DiceFace.values().random()
}