package fr.learnandrun.kingofheroes.business

import fr.learnandrun.kingofheroes.business.dice.DiceFace

class IA(
    board: Board,
    hero: Hero
) : Player(board, hero) {

    override suspend fun rollDices(numberOfDice: Int): List<DiceFace> =
        defaultRollDice(numberOfDice)

}