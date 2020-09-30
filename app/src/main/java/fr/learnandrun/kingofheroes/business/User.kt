package fr.learnandrun.kingofheroes.business

import fr.learnandrun.kingofheroes.business.dice.DiceFace

class User(
    hero: Hero
) : Player(hero) {

//    override suspend fun waitForRollClick(board: Board) {
//        board.waitForResume()
//    }
//
//    override suspend fun waitForReRollOrPassClick(board: Board, dices: MutableList<DiceFace?>) {
//        board.waitForResume()
//    }
//
//    override suspend fun waitForEndRollClick(board: Board) {
//        board.waitForResume()
//    }
//
//    override suspend fun decreaseHealth(board: Board, value: Int) {
//        super.decreaseHealth(board, value)
//        if (board.playerInsideCity == this && !isDead()) {
//            if (board.boardViewModel.proposeToLeaveTheCity())
//                board.playerInsideCity = null
//        }
//    }
}