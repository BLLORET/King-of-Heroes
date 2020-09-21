package fr.learnandrun.kingofheroes.business

import androidx.lifecycle.MutableLiveData
import fr.learnandrun.kingofheroes.business.dice.DiceFace
import fr.learnandrun.kingofheroes.tools.delegate.RangeDelegate

abstract class Player(
    val hero: Hero
) {
    val healthLiveData: MutableLiveData<Int> = MutableLiveData(DEFAULT_HEALTH)
    val victoryPointsLiveData: MutableLiveData<Int> = MutableLiveData(DEFAULT_POINTS)
    val energyLiveData: MutableLiveData<Int> = MutableLiveData(DEFAULT_ENERGY)

    private var health: Int by RangeDelegate(healthLiveData, MIN_HEALTH, MAX_HEALTH)
    private var victoryPoints: Int by RangeDelegate(victoryPointsLiveData, MIN_POINTS, MAX_POINTS)
    private var energy: Int by RangeDelegate(energyLiveData, MIN_ENERGY, MAX_ENERGY)

    fun isDead(): Boolean = health == MIN_HEALTH

    fun hasEnoughPointsToWin() = victoryPoints == MAX_POINTS

    abstract suspend fun waitForRollClick(board: Board)
    abstract suspend fun waitForReRollOrPassClick(board: Board, dices: MutableList<DiceFace?>)
    abstract suspend fun waitForEndRollClick(board: Board)

    fun increaseHealth(value: Int = 1) {
        health += value
    }
    open suspend fun decreaseHealth(board: Board, value: Int = 1) {
        health -= value
    }

    fun increaseVictoryPoints(value: Int = 1) {
        victoryPoints += value
    }
    fun decreaseVictoryPoints(value: Int = 1) {
        victoryPoints -= value
    }

    fun increaseEnergy(value: Int = 1) {
        energy += value
    }
    fun decreaseEnergy(value: Int = 1) {
        energy -= value
    }

    companion object {
        const val DEFAULT_HEALTH = 10
        const val MIN_HEALTH = 0
        const val MAX_HEALTH = 10

        const val DEFAULT_POINTS = 0
        const val MIN_POINTS = 0
        const val MAX_POINTS = 20

        const val DEFAULT_ENERGY = 0
        const val MIN_ENERGY = 0
        const val MAX_ENERGY = Int.MAX_VALUE
    }
}