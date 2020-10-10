package fr.learnandrun.kingofheroes.view_model

import androidx.lifecycle.ViewModel
import fr.learnandrun.kingofheroes.business.shop.AttackCard

class ShopViewModel(val partyViewModel: PartyViewModel) : ViewModel() {

    val attacks: List<AttackCard> = AttackCard.values().toList()

    fun onSelect() {
        partyViewModel.currentPlayer
        partyViewModel.shop.getCard(0)
    }

    fun resumeGame() {
        partyViewModel.resumeGame()
    }

    fun trySelectCard(index: Int) {
        partyViewModel.apply {
            if (partyViewModel.currentPlayer.energy >= shop.getCard(index).card.price)
                shop.selectCard(index)
        }
    }
}