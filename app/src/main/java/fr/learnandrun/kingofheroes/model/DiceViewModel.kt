package fr.learnandrun.kingofheroes.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import fr.learnandrun.kingofheroes.business.dice.DiceFace

class DiceViewModel(application: Application): AndroidViewModel(application) {

    val dices: MutableLiveData<List<DiceFace?>> = MutableLiveData(
        generateSequence { null }.take(6).toList()
    )


}