package fr.learnandrun.kingofheroes.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SelectFighterViewModel : ViewModel() {
    val currentIndex: MutableLiveData<Int> = MutableLiveData(0)
}