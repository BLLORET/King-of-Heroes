package fr.learnandrun.kingofheroes.tools.delegate

import androidx.lifecycle.MutableLiveData
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * DelegateLiveData is a variant of [MutableLiveData].
 *
 * The differences are that:
 *
 *  - The value cannot be null. If it is, it means that the value
 *    has not been initialized and a [NullPointerException] will be thrown.
 *
 *  - You can use it as delegate to directly accede to the value.
 */
open class DelegateLiveData<T: Any>(
    initialValue: T? = null
) : MutableLiveData<T>(initialValue), ReadWriteProperty<Any?, T> {

    fun isInitialized(): Boolean {
        return super.getValue() != null
    }

    override fun getValue(): T {
        return super.getValue()
            ?: throw NullPointerException("The value has not been initialized!")
    }

    override fun setValue(value: T) {
        if (super.getValue() != value)
            super.setValue(value)
    }

    override operator fun getValue(thisRef: Any?, property: KProperty<*>): T =
        this.value

    override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = value
    }

}