package fr.learnandrun.kingofheroes.tools.delegate

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Instantiating a variable with this delegate ensure that the value stay between a range [minRange, maxRange]
 * If we set a value lower than the minRange it will be set to minRange
 * If we set a value higher than the maxRange it will be set to maxRange
 */
class RangeDelegate(
    defaultValue: Int,
    private val minRange: Int,
    private val maxRange: Int
) : ReadWriteProperty<Any?, Int> {

    init {
        require(defaultValue in minRange..maxRange)
    }

    private var value = defaultValue

    override fun getValue(thisRef: Any?, property: KProperty<*>): Int = value

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
        this.value = when {
            value < minRange -> minRange
            value > maxRange -> maxRange
            else -> value
        }
    }
}