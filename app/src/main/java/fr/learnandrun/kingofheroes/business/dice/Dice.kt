package fr.learnandrun.kingofheroes.business.dice

import fr.learnandrun.kingofheroes.tools.delegate.DelegateLiveData
import fr.learnandrun.kingofheroes.tools.delegate.DelegateNullableLiveData

class Dice(
    val isSelectedLive: DelegateLiveData<Boolean> = DelegateLiveData(false),
    val diceFaceLive: DelegateNullableLiveData<DiceFace> = DelegateNullableLiveData()
) {

    var isSelected by isSelectedLive
    var diceFace by diceFaceLive

    fun selectSwap() {
        isSelected = !isSelected
    }

    fun roll(): DiceFace {
        isSelected = false
        val diceFace = DiceFace.values().random()
        this.diceFace = diceFace
        return diceFace
    }

    fun clear() {
        diceFace = null
        isSelected = false
    }

}