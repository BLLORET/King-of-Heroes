package fr.learnandrun.kingofheroes.business

import fr.learnandrun.kingofheroes.business.dice.DiceFace

class User(
    board: Board,
    hero: Hero
) : Player(board, hero) {

    override suspend fun showRollDicesButton() {
        TODO("Not yet implemented")
    }

    override suspend fun waitForRollClick() {
        TODO("Not yet implemented")
    }

    override suspend fun waitForReRollOrPassClick(dices: MutableList<DiceFace?>) {
        TODO("Not yet implemented")
    }

    override suspend fun waitForEndRollClick() {
        TODO("Not yet implemented")
    }

    override suspend fun decreaseHealth(value: Int) {
        TODO("Not yet implemented")
    }
}