package fr.learnandrun.kingofheroes.business

import fr.learnandrun.kingofheroes.business.dice.DiceFace

class User(
    hero: Hero
) : Player(hero) {

    override suspend fun rollDice(): DiceFace {
        //TODO: wait for the user to ask for roll
        return defaultRollDice()
    }
}