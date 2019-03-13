package com.orynastarkina.sounds.base

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.Lifecycle

/**
 * Created by Oryna Starkina on 04.01.2019.
 */
abstract class BaseViewModel<T: IRepository>(protected val repository: T): LifecycleOwner {

    // todo: check warning
    private var mLifecycleRegistry : LifecycleRegistry = LifecycleRegistry(this)

    init {
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
    }

    override fun getLifecycle(): Lifecycle {
        return mLifecycleRegistry
    }

    fun stopListening() {
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
    }

    fun startListening() {
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
    }
}