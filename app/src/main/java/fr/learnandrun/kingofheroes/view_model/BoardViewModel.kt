package fr.learnandrun.kingofheroes.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.learnandrun.kingofheroes.ui.view.LeaveCityAlertView
import kotlinx.coroutines.launch

class BoardViewModel(val partyViewModel: PartyViewModel) : ViewModel() {

    fun leaveTheCity(leaveTheCityAlertView: LeaveCityAlertView) = viewModelScope.launch {
        val leaveCity = leaveTheCityAlertView.suspendShow()
        partyViewModel.resumeLeaveCity(leaveCity)
    }

}