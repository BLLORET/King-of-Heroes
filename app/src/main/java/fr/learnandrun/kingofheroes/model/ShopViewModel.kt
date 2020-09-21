package fr.learnandrun.kingofheroes.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.learnandrun.kingofheroes.business.Attack

class ShopViewModel : ViewModel() {
    lateinit var attacks: MutableLiveData<List<Attack>>

    init {
        val allAttacks = Attack.values().toMutableSet()
        val firstAttack = allAttacks.random()
        allAttacks.remove(firstAttack)
        val secondAttack = allAttacks.random()
        allAttacks.remove(secondAttack)
        val thirdAttack = allAttacks.random()

        attacks.postValue(arrayListOf(firstAttack, secondAttack, thirdAttack))
    }
}