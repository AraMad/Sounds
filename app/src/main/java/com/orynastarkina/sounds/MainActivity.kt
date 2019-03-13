package com.orynastarkina.sounds

import android.os.Bundle
import com.orynastarkina.sounds.base.BaseFlowableActivity
import com.orynastarkina.sounds.base.FragmentFlow
import com.orynastarkina.sounds.flows.main.MainFlow


class MainActivity : BaseFlowableActivity() {

    override lateinit var currentFlow: FragmentFlow<*>

    override fun getContentViewLayoutId(): Int = R.layout.activity_main

    override fun onFlowFinished(flowTag: String, bundle: Bundle?) = finish()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        currentFlow = MainFlow(this.supportFragmentManager,
            this.application,
            R.id.container)
        super.onCreate(savedInstanceState)
    }
}
