package com.orynastarkina.sounds.flows.main.fragments

import android.arch.lifecycle.Observer
import android.support.v4.content.ContextCompat
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import com.google.android.gms.ads.AdRequest
import com.orynastarkina.sounds.R
import com.orynastarkina.sounds.base.BaseFlowFragment
import com.orynastarkina.sounds.flows.main.MainFlow
import com.orynastarkina.sounds.flows.main.MainViewModel
import com.orynastarkina.sounds.flows.main.dataLayer.utils.DefaultSounds
import com.orynastarkina.sounds.flows.main.dataLayer.utils.MainSound
import com.orynastarkina.sounds.flows.main.fragments.adapters.ImageAdapter
import com.orynastarkina.sounds.utils.Event
import kotlinx.android.synthetic.main.fragment_main_flow_dashboard.*
import kotlinx.android.synthetic.main.sound_item_view.view.*

/**
 * Created by Oryna Starkina on 04.01.2019.
 */
class Dashboard : BaseFlowFragment<MainViewModel>(MainFlow.MainFlowFragments.DASHBOARD) {

    override fun obtainViewModel(): MainViewModel = flowReference?.get()?.viewModel as MainViewModel

    override fun getContentViewLayoutId(): Int = R.layout.fragment_main_flow_dashboard

    override fun onViewModelReady() {

        viewModel.initMainSound()
        viewModel.initDashboardSounds()

        viewModel.fragmentFinishEvent.observe(this, Observer {
            it?.getContentIfNotHandled()?.let {
                flowReference?.get()?.onFragmentFinished(fragmentDescriptor.getTag())
            }
        })


        viewModel.isMainSoundDataChanges.observe(this,
            android.arch.lifecycle.Observer<Event<MainSound>> {
                it?.getContentIfNotHandled()?.let {
                    logoSound.setImageResource(DefaultSounds.valueOf(it.coverPath).getCoverId())
                }
        })
        viewModel.isDashboardDataChanges.observe(this,
            android.arch.lifecycle.Observer<Event<Boolean>> {
                it?.getContentIfNotHandled()?.let {
                    (gridview.adapter as ImageAdapter).notifyDataSetChanged()
                }
            })

        gridview.adapter = ImageAdapter(this.activity!!.applicationContext, viewModel.dashboardSounds)

        gridview.onItemClickListener =
            AdapterView.OnItemClickListener { _, v, position, _ ->

                // UI thing belong to View
                v.itemImage.startAnimation(
                    AnimationUtils
                        .loadAnimation(this.activity!!.applicationContext, R.anim.rotate)
                )

                // Logic thing belong to ViewModel
                viewModel.dashboardItemClicked(v.context, position)
            }

        gridview.onItemLongClickListener =
            AdapterView.OnItemLongClickListener { _, v, position, _ ->

                // UI thing belong to View
                v.setBackgroundColor(ContextCompat.getColor(v.context, R.color.colorPrimaryHalOpacity))

                // Logic thing belong to ViewModel
                viewModel.dashboardLongItemClicked(position)

                return@OnItemLongClickListener true
            }


        logoSound.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(this.activity!!.applicationContext, R.anim.scale))

            viewModel.mainSoundClicked(it.context)
        }

        logoSound.setOnLongClickListener {

            // UI thing belong to View
            it.setBackgroundColor(ContextCompat.getColor(it.context, R.color.colorPrimaryHalOpacity))

            // Logic thing belong to ViewModel
            viewModel.mainSoundLongClick()

            return@setOnLongClickListener true
        }

        adView.loadAd(AdRequest.Builder().build())
    }

    override fun onPause() {
        super.onPause()

        viewModel.viewPaused()
    }
}