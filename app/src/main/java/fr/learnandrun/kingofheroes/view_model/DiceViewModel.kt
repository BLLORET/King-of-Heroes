package fr.learnandrun.kingofheroes.view_model

import androidx.lifecycle.ViewModel

class DiceViewModel(val partyViewModel: PartyViewModel): ViewModel() {

    fun selectDice(index: Int) {
        partyViewModel.dicePool.getDice(index).selectSwap()
    }

    fun throwOrPass() {
        partyViewModel.resumeGame()
    }

}