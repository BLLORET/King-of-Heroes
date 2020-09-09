package fr.learnandrun.kingofheroes.business

import fr.learnandrun.kingofheroes.business.dice.DiceFace

class Board(
    val players: List<Player>
) {

    val turnLoop = TurnLoop(players)
    val playerInsideCity: Player? = null
    val diceFaceResults = mutableListOf<DiceFace>()


    fun initBoard() {
        turnLoop.setFirstPlayer(TODO())
    }

    fun nextAlivePlayer(): Player {
        check(!players.all { it.isDead() })
        var nextPlayer = turnLoop.nextPlayer()
        while (nextPlayer.isDead())
            nextPlayer = turnLoop.nextPlayer()
        return nextPlayer
    }


    fun endTurn() {
        diceFaceResults.clear()
    }

}