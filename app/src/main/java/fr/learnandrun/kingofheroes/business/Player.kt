package fr.learnandrun.kingofheroes.business

import fr.learnandrun.kingofheroes.business.dice.Dice
import fr.learnandrun.kingofheroes.business.dice.DiceFace
import fr.learnandrun.kingofheroes.tools.delegate.RangeDelegate

abstract class Player(
    val board: Board,
    val hero: Hero
) {
    var health: Int by RangeDelegate(DEFAULT_HEALTH, MIN_HEALTH, MAX_HEALTH)
    var victoryPoints: Int by RangeDelegate(DEFAULT_POINTS, MIN_POINTS, MAX_POINTS)
    var energy: Int = 0

    fun isDead(): Boolean = health == MIN_HEALTH

    fun hasEnoughPointsToWin() = victoryPoints == MAX_POINTS

    abstract suspend fun rollDices(numberOfDice: Int): List<DiceFace>

    companion object {
        const val DEFAULT_HEALTH = 10
        const val MIN_HEALTH = 0
        const val MAX_HEALTH = 10

        const val DEFAULT_POINTS = 0
        const val MIN_POINTS = 0
        const val MAX_POINTS = 20

        suspend fun Player.defaultRollDice(numberOfDice: Int): List<DiceFace> {
            val diceFaceResults = generateSequence { Dice.roll() }
                .take(numberOfDice)
                .toList()
            board.boardViewModel.showRollDiceAnimation(diceFaceResults)
            return diceFaceResults
        }
    }
}