package fr.learnandrun.kingofheroes.business

import fr.learnandrun.kingofheroes.business.dice.DiceFace

class User(
    board: Board,
    hero: Hero
) : Player(board, hero) {

    override suspend fun rollDices(numberOfDice: Int): List<DiceFace> {
        board.boardViewModel.showRollDiceButton()
        return defaultRollDice(numberOfDice)
    }
}