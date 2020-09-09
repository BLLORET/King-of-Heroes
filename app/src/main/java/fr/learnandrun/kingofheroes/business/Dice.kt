package fr.learnandrun.kingofheroes.business

enum class Dice {
    ONE,
    TWO,
    THREE,
    LIGHTNING,
    SLAP,
    HEART;

    fun roll(): Dice = values().random()
}