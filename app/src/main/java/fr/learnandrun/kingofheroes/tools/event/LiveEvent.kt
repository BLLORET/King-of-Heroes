package fr.learnandrun.kingofheroes.tools.event

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

/**
 * LiveEvent permits to make events with EventArgs of type [T]
 * It is different from a live data, because the observers (subscribers of the event)
 * are only notified when the trigger function is called
 */
class LiveEvent<T: Any?> {

    private data class Box<T>(
        var value: T
    )

    private val liveData = MutableLiveData<Box<T>?>(null)
    private val reentrantLock = ReentrantLock()

    /**
     * Subscribe to the event. The [event] function will be called at each call to [trigger]
     */
    fun subscribe(owner: LifecycleOwner, event: (eventArgs: T) -> Unit) {
        liveData.observe(owner) {
            reentrantLock.withLock {
                liveData.value?.let {
                    event(it.value)
                }
            }
        }
    }

    /**
     * Trigger the event: Notify all subscribers ([subscribe]) of the event
     */
    fun trigger(eventArgs: T) {
        reentrantLock.withLock {
            liveData.value = Box(eventArgs)
            liveData.value = null
        }
    }

}