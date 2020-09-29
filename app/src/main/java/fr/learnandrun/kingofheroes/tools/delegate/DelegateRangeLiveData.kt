package fr.learnandrun.kingofheroes.tools.delegate

import androidx.lifecycle.MutableLiveData
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * DelegateRangeLiveData is a variant of [MutableLiveData].
 *
 * The differences are that:
 *
 *  - The value cannot be null. If it is, it means that the value
 *    has not been initialized and a [NullPointerException] will be thrown.
 *
 *  - You can use it as delegate to directly accede to the value.
 *
 *  - The value is bound to [[minRange], [maxRange]]
 */
open class DelegateRangeLiveData(
    initialValue: Int,
    private val minRange: Int,
    private val maxRange: Int
) : MutableLiveData<Int>(initialValue), ReadWriteProperty<Any?, Int> {

    init {
        require(initialValue in minRange..maxRange)
    }

    override fun getValue(): Int {
        return super.getValue()
            ?: throw NullPointerException("The value has not been initialized!")
    }

    override fun setValue(value: Int) {
        if (super.getValue() != value)
            super.setValue(value)
    }

    override operator fun getValue(thisRef: Any?, property: KProperty<*>): Int =
        this.value

    override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
        this.value = when {
            value < minRange -> minRange
            value > maxRange -> maxRange
            else -> value
        }
    }

}