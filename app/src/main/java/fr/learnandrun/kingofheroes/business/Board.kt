package fr.learnandrun.kingofheroes.business

import fr.learnandrun.kingofheroes.tools.delegate.DelegateNullableLiveData

class Board {

    lateinit var players: List<Player>

    val playerInsideCityLive = DelegateNullableLiveData<Player?>(null)
    var playerInsideCity: Player? by playerInsideCityLive

    fun initBoard(players: List<Player>) {
        this.players = players
        this.playerInsideCity = null
    }

}