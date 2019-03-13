package com.orynastarkina.sounds.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by Oryna Starkina on 04.01.2019.
 */
abstract class BaseFlowableActivity:
    AppCompatActivity(), FlowLifecycleListener {

    abstract var currentFlow: FragmentFlow<*>

    abstract fun getContentViewLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(getContentViewLayoutId())

        currentFlow.setFlowLifecycleListener(this)
        currentFlow.startFlow()
    }

    override fun onBackPressed() {
        super.onBackPressed()

        currentFlow.backPressed()
    }


    fun startNewFlow(flow: FragmentFlow<*>, bundle: Bundle? = null){
        currentFlow = flow
        currentFlow.setFlowLifecycleListener(this)
        currentFlow.startFlow(bundle)
    }
}