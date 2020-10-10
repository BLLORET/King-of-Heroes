package fr.learnandrun.kingofheroes.business.shop

class Shop {

    val cards = List(CARD_AMOUNT) { Card() }
    var currentSelectedCard: Card? = null

    fun getCard(index: Int): Card {
        check(index in 0..CARD_AMOUNT)
        return cards[index]
    }

    fun selectCard(index: Int) {
        check(index in 0..CARD_AMOUNT)

        // deselect the current selected card
        currentSelectedCard?.selectSwap()

        val card = cards[index]
        currentSelectedCard = if (card != currentSelectedCard) card else null

        // select the new card (if any selected)
        currentSelectedCard?.selectSwap()
    }

    fun initShop() {
        currentSelectedCard = null
        cards.forEach { it.random() }
    }

    fun openShop() {
        currentSelectedCard = null
        cards.forEach {
            it.clear()
        }
    }

    fun closeShop() {
        currentSelectedCard?.random()
    }

    companion object {
        const val CARD_AMOUNT = 3
    }

}