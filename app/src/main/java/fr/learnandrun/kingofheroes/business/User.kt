package fr.learnandrun.kingofheroes.business

import fr.learnandrun.kingofheroes.business.dice.DiceFace

class User(
    hero: Hero
) : Player(hero) {

    override suspend fun rollDices(numberOfDice: Int): List<DiceFace> {
        board.boardViewModel.showRollDiceButton()
        return defaultRollDice(numberOfDice)
    }
}