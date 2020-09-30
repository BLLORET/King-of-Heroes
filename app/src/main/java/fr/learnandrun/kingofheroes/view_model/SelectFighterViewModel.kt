package fr.learnandrun.kingofheroes.view_model

import android.view.View
import androidx.lifecycle.ViewModel
import fr.learnandrun.kingofheroes.business.Hero
import fr.learnandrun.kingofheroes.business.IA
import fr.learnandrun.kingofheroes.business.User
import fr.learnandrun.kingofheroes.tools.delegate.DelegateLiveData
import fr.learnandrun.kingofheroes.tools.delegate.DelegateNullableLiveData

class SelectFighterViewModel(val partyViewModel: PartyViewModel) : ViewModel() {

    private var currentIndex = 0

    val backArrowIsVisibleLive = DelegateLiveData(View.GONE)
    val nextArrowIsVisibleLive = DelegateLiveData(View.VISIBLE)

    var backArrowIsVisible by backArrowIsVisibleLive
    var nextArrowIsVisible by nextArrowIsVisibleLive

    val imageBackIdLive = DelegateNullableLiveData<Int>(null)
    val imageCurrentIdLive = DelegateNullableLiveData(Hero.values().first().imageId)
    val imageNextIdLive = DelegateNullableLiveData(Hero.values()[currentIndex + 1].imageId)

    var imageBackId by imageBackIdLive
    var imageCurrentId by imageCurrentIdLive
    var imageNextId by imageNextIdLive

    private fun update() {
        nextArrowIsVisible = if (currentIndex != Hero.values().size - 1) View.VISIBLE else View.GONE
        backArrowIsVisible = if (currentIndex != 0) View.VISIBLE else View.GONE
        imageBackId =
            if (currentIndex == 0)
                null
            else
                Hero.values()[currentIndex - 1].imageId
        imageCurrentId = Hero.values()[currentIndex].imageId
        imageNextId =
            if (currentIndex == Hero.values().size - 1)
                null
            else
                Hero.values()[currentIndex + 1].imageId
    }

    fun next() {
        currentIndex++
        update()
    }

    fun back() {
        currentIndex--
        update()
    }

    fun choose() {
        val selectedHero = Hero.atIndex(currentIndex)
        val heroes = Hero.values().toMutableSet()
        heroes.remove(selectedHero)

        // listOf take varargs and the operator "*" convert type array in multiple args
        // The result of this is a List<Player> with a user and 3 IAs
        partyViewModel.initParty(
            listOf(User(selectedHero),
                *(1..3).map {
                    IA(heroes.random().also { heroes.remove(it) })
                }.toTypedArray()
            )
        )
    }

}