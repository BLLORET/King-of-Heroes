package fr.learnandrun.kingofheroes.business

import fr.learnandrun.kingofheroes.business.dice.DiceFace

class User(
    hero: Hero
) : Player(hero) {

    override suspend fun waitForRollClick(board: Board) {
        board.waitForResume()
    }

    override suspend fun waitForReRollOrPassClick(board: Board, dices: MutableList<DiceFace?>) {
        //TODO("Not yet implemented")
        board.waitForResume()
    }

    override suspend fun waitForEndRollClick(board: Board) {
        //TODO("Not yet implemented")
    }

    override suspend fun decreaseHealth(board: Board, value: Int) {
        super.decreaseHealth(board, value)
        //TODO("Not yet implemented")
    }
}