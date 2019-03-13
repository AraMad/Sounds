package com.orynastarkina.sounds.base

import java.lang.ref.WeakReference

/**
 * Created by Oryna Starkina on 04.01.2019.
 */
abstract class BaseFlowFragment<V: BaseViewModel<*>>(val fragmentDescriptor: IFlowFragment) : BaseFragment<V>(){

    // todo: think to do lateinit
    protected var flowReference: WeakReference<FragmentFlow<*>>? = null

    fun setFlow(flow: FragmentFlow<*>) {
        flowReference = WeakReference(flow)
    }
}