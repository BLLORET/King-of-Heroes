package fr.learnandrun.kingofheroes.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SelectFighterViewModel : ViewModel() {
    val currentIndex: MutableLiveData<Int> = MutableLiveData(0)
}