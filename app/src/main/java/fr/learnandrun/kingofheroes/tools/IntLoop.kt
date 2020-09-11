package fr.learnandrun.kingofheroes.tools

class IntLoop(
    startValue: Int,
    private val minRange: Int,
    private val maxRange: Int
) {

    var value = startValue

    init {
        require(startValue in minRange..maxRange)
    }

    fun nextValue(whenCycling: () -> Unit): Int {
        val actualValue = value++
        if (value > maxRange) {
            value = minRange
            whenCycling()
        }
        return actualValue
    }


}