package fr.learnandrun.kingofheroes.business.shop

import fr.learnandrun.kingofheroes.tools.delegate.DelegateLiveData

class Card(
    val isSelectedLive: DelegateLiveData<Boolean> = DelegateLiveData(false),
    val cardLive: DelegateLiveData<AttackCard> = DelegateLiveData()
) {

    var isSelected by isSelectedLive
    var card by cardLive

    fun selectSwap() {
        isSelected = !isSelected
    }

    fun random(): AttackCard {
        val attackCard = AttackCard.values().random()
        this.card = attackCard
        return attackCard
    }

    fun clear() {
        isSelected = false
    }

}