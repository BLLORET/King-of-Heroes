package fr.learnandrun.kingofheroes.tools.delegate

import androidx.lifecycle.MutableLiveData
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * DelegateNullableLiveData is a variant of [MutableLiveData].
 *
 * The differences is that you can use it as delegate to directly accede to the value.
 */
open class DelegateNullableLiveData<T: Any?>(
    initialValue: T? = null
) : MutableLiveData<T>(initialValue), ReadWriteProperty<Any?, T?> {

    override fun setValue(value: T?) {
        if (super.getValue() != value)
            super.setValue(value)
    }

    override operator fun getValue(thisRef: Any?, property: KProperty<*>): T? =
        this.value

    override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        this.value = value
    }

}