package fr.learnandrun.kingofheroes.tools.delegate

import androidx.lifecycle.MutableLiveData
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Instantiating a variable with this delegate ensure that the value stay between a range [minRange, maxRange]
 * This variable is attached to $liveData
 * If we set a value lower than the minRange it will be set to minRange
 * If we set a value higher than the maxRange it will be set to maxRange
 */
class RangeDelegate(
    private val liveData: MutableLiveData<Int>,
    private val minRange: Int,
    private val maxRange: Int
) : ReadWriteProperty<Any?, Int> {

    init {
        require(liveData.value in minRange..maxRange)
    }

    private var value: Int = liveData.value
        ?: throw IllegalStateException("The value must not be null")

    override fun getValue(thisRef: Any?, property: KProperty<*>): Int = value

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
        this.value = when {
            value < minRange -> minRange
            value > maxRange -> maxRange
            else -> value
        }
        liveData.value = this.value
    }
}