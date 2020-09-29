package fr.learnandrun.kingofheroes.business

import fr.learnandrun.kingofheroes.tools.delegate.DelegateRangeLiveData

abstract class Player(
    val hero: Hero
) {
    val healthLiveData = DelegateRangeLiveData(DEFAULT_HEALTH, MIN_HEALTH, MAX_HEALTH)
    val victoryPointsLiveData = DelegateRangeLiveData(DEFAULT_POINTS, MIN_POINTS, MAX_POINTS)
    val energyLiveData = DelegateRangeLiveData(DEFAULT_ENERGY, MIN_ENERGY, MAX_ENERGY)

    var health: Int by healthLiveData
    var victoryPoints: Int by victoryPointsLiveData
    var energy: Int by energyLiveData

    fun isDead(): Boolean = health == MIN_HEALTH

    fun hasEnoughPointsToWin() = victoryPoints == MAX_POINTS

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