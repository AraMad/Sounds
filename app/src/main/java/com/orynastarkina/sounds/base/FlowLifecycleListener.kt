package com.orynastarkina.sounds.base

import android.os.Bundle

/**
 * Created by Oryna Starkina on 10.01.2019.
 */
interface FlowLifecycleListener {

    fun onFlowFinished(flowTag: String, bundle: Bundle? = null)
}