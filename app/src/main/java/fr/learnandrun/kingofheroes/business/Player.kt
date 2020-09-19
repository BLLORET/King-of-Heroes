package fr.learnandrun.kingofheroes.business

import fr.learnandrun.kingofheroes.business.dice.DiceFace
import fr.learnandrun.kingofheroes.tools.delegate.RangeDelegate

abstract class Player(
    val hero: Hero
) {
    lateinit var board: Board

    private var health: Int by RangeDelegate(DEFAULT_HEALTH, MIN_HEALTH, MAX_HEALTH)
    private var victoryPoints: Int by RangeDelegate(DEFAULT_POINTS, MIN_POINTS, MAX_POINTS)
    private var energy: Int = 0

    fun isDead(): Boolean = health == MIN_HEALTH

    fun hasEnoughPointsToWin() = victoryPoints == MAX_POINTS

    abstract suspend fun waitForRollClick()
    abstract suspend fun waitForReRollOrPassClick(dices: MutableList<DiceFace?>)
    abstract suspend fun waitForEndRollClick()

    fun increaseHealth(value: Int = 1) {
        health -= value
    }
    abstract suspend fun decreaseHealth(value: Int = 1)

    fun increaseVictoryPoints(value: Int = 1) {
        victoryPoints += value
    }
    fun decreaseVictoryPoints(value: Int = 1) {
        victoryPoints -= value
    }

    fun increaseEnergy(value: Int = 1) {
        victoryPoints += value
    }
    fun decreaseEnergy(value: Int = 1) {
        victoryPoints -= value
    }

    companion object {
        const val DEFAULT_HEALTH = 10
        const val MIN_HEALTH = 0
        const val MAX_HEALTH = 10

        const val DEFAULT_POINTS = 0
        const val MIN_POINTS = 0
        const val MAX_POINTS = 20
    }
}