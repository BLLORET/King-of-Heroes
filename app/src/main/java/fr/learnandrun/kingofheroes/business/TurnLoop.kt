package fr.learnandrun.kingofheroes.business

import fr.learnandrun.kingofheroes.tools.IntLoop

class TurnLoop(
    private val players: List<Player>
) : Iterable<Player>, Iterator<Player> {

    private lateinit var intLoop: IntLoop
    private var actualTurn = 1
    private var actualRound = 1

    fun setFirstPlayer(player: Player) {
        intLoop = IntLoop(players.indexOf(player), 0, players.size)
    }

    fun nextPlayer(): Player {
        check(::intLoop.isInitialized) { "You need to set first player before starting" }
        check(hasNext()) { "There is no next player alive!" }
        actualTurn++
        return players[intLoop.nextValue { actualRound++ }]
            .takeUnless { it.isDead() }
            ?: nextPlayer()
    }

    fun isFirstRound(): Boolean = actualTurn == 1

    fun getActualRound(): Int = actualRound

    override fun iterator(): Iterator<Player> {
        return this
    }

    override fun hasNext(): Boolean {
        return !players.all { it.isDead() }
    }

    override fun next(): Player {
        return nextPlayer()
    }

}