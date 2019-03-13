package com.orynastarkina.sounds.flows.main

import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentManager
import com.orynastarkina.sounds.base.*
import com.orynastarkina.sounds.flows.main.dataLayer.MainFlowRepository
import com.orynastarkina.sounds.flows.main.dataLayer.SoundDatabaseManager
import com.orynastarkina.sounds.flows.main.fragments.AddSoundFragment
import com.orynastarkina.sounds.flows.main.fragments.Dashboard
import com.orynastarkina.sounds.flows.main.fragments.RecordSoundFragment

/**
 * Created by Oryna Starkina on 04.01.2019.
 */
class MainFlow(
    fragmentManager: FragmentManager,
    appContext: Context,
    fragmentContainer: Int
) : FragmentFlow<MainFlow.MainFlowFragments>(
    MainFlow::class.java.simpleName,
    fragmentManager,
    fragmentContainer
) {

    init {
        viewModel = MainViewModel(MainFlowRepository(SoundDatabaseManager.getInstance(appContext)))
    }

    override fun startFlow(bundle: Bundle?) {
        attachFragment(MainFlowFragments.DASHBOARD, false, true)
    }

    override fun initFragment(fragmentDescriptor: MainFlow.MainFlowFragments): BaseFlowFragment<*> {

        val fragment = when (fragmentDescriptor) {

            MainFlowFragments.DASHBOARD -> {
                Dashboard()
            }

            MainFlowFragments.ADD_SOUND -> {
                AddSoundFragment()
            }

            MainFlowFragments.RECORD_AUDIO -> {
                RecordSoundFragment()
            }

        }

        fragment.setFlow(this)

        return fragment
    }

    override fun onFragmentFinished(fragmentTag: String, jumpTo: String) {
        when (fragmentTag){
            MainFlowFragments.DASHBOARD.getTag() -> {
                attachFragment(MainFlowFragments.ADD_SOUND, true, true)
            }

            MainFlowFragments.ADD_SOUND.getTag() -> {
                if (jumpTo.isNotEmpty()) {
                    attachFragment(MainFlowFragments.valueOf(jumpTo), true, true)
                } else {
                    detachFragmentOrFinishFlow()
                }
            }

            MainFlowFragments.RECORD_AUDIO.getTag() -> {
                if (jumpTo.isNotEmpty()) {
                    attachFragment(MainFlowFragments.valueOf(jumpTo), true, true)
                } else {
                    detachFragmentOrFinishFlow()
                }
            }
        }
    }


    enum class MainFlowFragments : IFlowFragment {
        DASHBOARD {
            override fun getTag(): String = this.toString()
        },
        ADD_SOUND {
            override fun getTag(): String = this.toString()
        },
        RECORD_AUDIO {
            override fun getTag(): String = this.toString()
        }
    }
}