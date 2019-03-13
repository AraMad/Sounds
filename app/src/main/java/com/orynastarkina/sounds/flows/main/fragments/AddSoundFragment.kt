package com.orynastarkina.sounds.flows.main.fragments

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.orynastarkina.sounds.base.BaseFlowFragment
import com.orynastarkina.sounds.flows.main.MainFlow
import com.orynastarkina.sounds.flows.main.MainViewModel
import com.orynastarkina.sounds.flows.main.fragments.adapters.AddSoundsAdapter
import com.orynastarkina.sounds.utils.Event
import kotlinx.android.synthetic.main.fragment_main_flow_add_sound.*


/**
 * Created by Oryna Starkina on 09.01.2019.
 */
class AddSoundFragment : BaseFlowFragment<MainViewModel>(MainFlow.MainFlowFragments.ADD_SOUND) {

    private val GET_SOUND_EQUEST_CODE = 101

    override fun obtainViewModel(): MainViewModel = flowReference?.get()?.viewModel as MainViewModel

    override fun getContentViewLayoutId(): Int = com.orynastarkina.sounds.R.layout.fragment_main_flow_add_sound

    override fun onViewModelReady() {

        viewModel.initAllSounds()

        viewModel.fragmentFinishEvent.observe(this, Observer {
            it?.getContentIfNotHandled()?.let {
                flowReference?.get()?.onFragmentFinished(fragmentDescriptor.getTag())
            }
        })

        viewModel.isSoundsDataChanges.observe(this,
            android.arch.lifecycle.Observer<Event<Boolean>> {
                it?.getContentIfNotHandled()?.let {
                    (recycleViewSoundList.adapter as AddSoundsAdapter).notifyDataSetChanged()
                }
            })

        recycleViewSoundList.adapter = AddSoundsAdapter(viewModel.sounds, viewModel::addSoundToDashboard)

        recycleViewSoundList.setHasFixedSize(true)
        recycleViewSoundList.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )

        microphoneFab.setOnClickListener {
            flowReference?.get()?.onFragmentFinished(
                fragmentDescriptor.getTag(),
                MainFlow.MainFlowFragments.RECORD_AUDIO.getTag()
            )
        }

        memoryFab.setOnClickListener {
            val intentGetSound = Intent()
            intentGetSound.type = "audio/*"
            intentGetSound.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intentGetSound, GET_SOUND_EQUEST_CODE)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (resultCode) {
            Activity.RESULT_OK -> {
                when (requestCode) {
                    GET_SOUND_EQUEST_CODE -> {
                        if (data?.data != null) {
                            viewModel.onNewSoundAdded(Uri.parse(data.data!!.path), data.data!!.lastPathSegment!!)
                        } else {
                            Toast.makeText(this.context, "null data", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}