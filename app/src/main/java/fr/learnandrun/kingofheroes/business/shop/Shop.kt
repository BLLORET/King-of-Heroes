package fr.learnandrun.kingofheroes.business.shop

class Shop {

    val cards = List(CARD_AMOUNT) { Card() }

    fun getCard(index: Int): Card {
        check(index in 0..CARD_AMOUNT)
        return cards[index]
    }

    fun initShop() {
        cards.forEach { it.random() }
    }

    fun openShop() {
        cards.forEach {
            it.clear()
        }
    }

    fun closeShop() {
        cards.filter { it.isSelected }.forEach { it.random() }
    }

    companion object {
        const val CARD_AMOUNT = 3
    }

}