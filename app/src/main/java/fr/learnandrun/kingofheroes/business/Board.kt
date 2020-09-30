package fr.learnandrun.kingofheroes.business

import fr.learnandrun.kingofheroes.tools.delegate.DelegateNullableLiveData

class Board(val players: List<Player>) {

    val playerInsideCityLive = DelegateNullableLiveData<Player>(null)
    var playerInsideCity: Player? by playerInsideCityLive

}