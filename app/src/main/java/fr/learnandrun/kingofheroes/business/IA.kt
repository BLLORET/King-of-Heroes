package fr.learnandrun.kingofheroes.business

import fr.learnandrun.kingofheroes.business.dice.DiceFace

class IA(
    hero: Hero
) : Player(hero) {

    override suspend fun rollDices(numberOfDice: Int): List<DiceFace> =
        defaultRollDice(numberOfDice)

}