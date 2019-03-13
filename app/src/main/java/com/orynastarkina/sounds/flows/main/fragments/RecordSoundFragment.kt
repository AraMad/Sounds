package com.orynastarkina.sounds.flows.main.fragments

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.content.pm.PackageManager
import android.os.SystemClock
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.orynastarkina.sounds.R
import com.orynastarkina.sounds.base.BaseFlowFragment
import com.orynastarkina.sounds.flows.main.MainFlow
import com.orynastarkina.sounds.flows.main.MainViewModel
import kotlinx.android.synthetic.main.audio_record_dialog.*


/**
 * Created by Oryna Starkina on 06.03.2019.
 */
class RecordSoundFragment : BaseFlowFragment<MainViewModel>(MainFlow.MainFlowFragments.RECORD_AUDIO) {

    private val REQUEST_PERMISSION_CODE = 201


    override fun obtainViewModel() = flowReference?.get()?.viewModel as MainViewModel

    override fun getContentViewLayoutId() = R.layout.audio_record_dialog

    override fun onViewModelReady() {

        viewModel.fragmentFinishEvent.observe(this, Observer {
            it?.getContentIfNotHandled()?.let {
                flowReference?.get()?.onFragmentFinished(fragmentDescriptor.getTag())
            }
        })


        startRecordingButton.setOnClickListener {

            if (!isPermissionsGranted()) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this.activity as Activity,
                        Manifest.permission.RECORD_AUDIO
                    )
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                        this.activity as Activity,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                ) {
                    showPermissionExplanation()
                } else {
                    startRequestingPermissions()
                }
            } else {
                startSoundRecording()
            }
        }

        stopRecordingButton.setOnClickListener {
            viewModel.onStopAudioRecordButtonClick()
            save.isEnabled = true
            startRecordingButton.isEnabled = true
            soundVisualizer.stop()
            soundVisualizer.base = SystemClock.elapsedRealtime()
        }

        save.isEnabled = false
        save.setOnClickListener {
            viewModel.onSaveRecordedAudioButtonClick()
        }

    }

    private fun isPermissionsGranted() =
        (ContextCompat.checkSelfPermission(this.activity!!.baseContext, Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    this.activity!!.baseContext,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                == PackageManager.PERMISSION_GRANTED
                )


    private fun showPermissionExplanation() {
        AlertDialog.Builder(this.activity)
            .setPositiveButton(android.R.string.ok) { dialogInterface, _ ->
                dialogInterface.cancel()
                startRequestingPermissions()
            }
            .setMessage(getString(R.string.permission_explanation))
            .create()
            .show()
    }

    private fun startRequestingPermissions() {
        requestPermissions(
            arrayOf(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            REQUEST_PERMISSION_CODE
        )
    }

    private fun startSoundRecording(){
        viewModel.onStartAudioRecordButtonClick()
        save.isEnabled = false
        startRecordingButton.isEnabled = false
        soundVisualizer.base = SystemClock.elapsedRealtime()
        soundVisualizer.start()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_PERMISSION_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isEmpty() || grantResults.any { it != PackageManager.PERMISSION_GRANTED })) {
                    showPermissionExplanation()
                } else {
                    startSoundRecording()
                }
                return
            }
            else -> {
                // Ignore all other requests.
            }
        }
    }
}