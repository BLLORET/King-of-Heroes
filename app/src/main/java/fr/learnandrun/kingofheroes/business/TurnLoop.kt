package fr.learnandrun.kingofheroes.business

class TurnLoop(
    private val players: List<Player>
) {
    var turnNumber = 0

    fun setFirstPlayer(player: Player) {
        turnNumber = players.indexOf(player)
    }

    fun nextPlayer(): Player = players[turnNumber++ % players.size]
    fun getActualRound() : Int = turnNumber / players.size + 1

}