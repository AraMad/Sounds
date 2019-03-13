package com.orynastarkina.sounds.flows.main

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.util.Log
import com.orynastarkina.sounds.base.BaseViewModel
import com.orynastarkina.sounds.flows.main.dataLayer.utils.DashboardSound
import com.orynastarkina.sounds.flows.main.dataLayer.IMainRepository
import com.orynastarkina.sounds.flows.main.dataLayer.utils.DefaultSounds
import com.orynastarkina.sounds.flows.main.dataLayer.utils.MainSound
import com.orynastarkina.sounds.flows.main.dataLayer.utils.Sound
import com.orynastarkina.sounds.utils.Event
import com.orynastarkina.sounds.utils.createAudioFile
import com.orynastarkina.sounds.utils.getAppFolder
import com.orynastarkina.sounds.utils.isDefayultSoundsContainsValue
import java.io.File
import java.io.IOException

/**
 * Created by Oryna Starkina on 04.01.2019.
 */
class MainViewModel(repository: IMainRepository) : BaseViewModel<IMainRepository>(repository), LifecycleOwner {

    private val TAG = this.javaClass.simpleName

    private var mediaPlayer: MediaPlayer? = null

    private var currentPressedItemPosition: Int = 0

    private var mainSound = DashboardSound(null, Sound(null, "", "", ""), -1, true)
    val isMainSoundDataChanges = MutableLiveData<Event<MainSound>>()

    val dashboardSounds = ArrayList<DashboardSound>()
    val isDashboardDataChanges = MutableLiveData<Event<Boolean>>() // todo: think change to Unit

    val sounds = ArrayList<Sound>()
    val isSoundsDataChanges = MutableLiveData<Event<Boolean>>()

    val fragmentFinishEvent = MutableLiveData<Event<Unit>>()

    private var currentRecordedSoundFile: File? = null


    fun initMainSound() {
        if (mainSound.id == null) {
            repository.getMainSound().observe(this, Observer {
                mainSound = it!!
                isMainSoundDataChanges.postValue(Event(MainSound(mainSound.sound.trackPath, mainSound.sound.coverPath)))
            })
        }
    }

    fun initDashboardSounds() {

        if (dashboardSounds.isEmpty()) {
            repository.getDashboardSounds().observe(this, Observer {
                it?.toCollection(dashboardSounds)
                isDashboardDataChanges.postValue(Event(true))
            })
        }
    }

    fun initAllSounds() {
        if (sounds.isEmpty()) {
            repository.getAllSounds().observe(this, Observer {
                it?.toCollection(sounds)
                isSoundsDataChanges.postValue(Event(true))
            })
        }
    }

    fun viewPaused() {
        stopAndRecyclePlayer()
        // todo: handle audio recording
    }

    fun mainSoundClicked(context: Context) {
        stopAndRecyclePlayer()
        playSound(context, mainSound.sound)
    }

    fun dashboardItemClicked(context: Context, position: Int) {
        stopAndRecyclePlayer()
        playSound(context, dashboardSounds[position].sound)
    }

    fun mainSoundLongClick() {
        stopAndRecyclePlayer()
        currentPressedItemPosition = mainSound.position

        fragmentFinishEvent.postValue(Event(Unit))
    }

    fun dashboardLongItemClicked(position: Int) {
        stopAndRecyclePlayer()
        currentPressedItemPosition = position

        fragmentFinishEvent.postValue(Event(Unit))
    }

    fun addSoundToDashboard(position: Int) {

        // todo: refresh view event

        if (currentPressedItemPosition == mainSound.position) {
            if (mainSound.sound.id != sounds[position].id) {
                repository.removeSoundFromDashboard(mainSound)
                mainSound.sound = sounds[position]
                repository.addSoundToDashboard(mainSound)
            }
        } else if (dashboardSounds[currentPressedItemPosition].sound.id != sounds[position].id) {
            repository.removeSoundFromDashboard(dashboardSounds[currentPressedItemPosition])
            dashboardSounds[currentPressedItemPosition].sound = sounds[position]
            repository.addSoundToDashboard(dashboardSounds[currentPressedItemPosition])
        }

        fragmentFinishEvent.postValue(Event(Unit))

    }


    // todo: think about icon change
    fun onNewSoundAdded(uri: Uri, title: String) {
        repository.saveSound(
            Sound(
                null, uri.toString(),
                title,
                DefaultSounds.ANGRY_TIMLID.toString()
            )
        ).apply {
            removeObservers(this@MainViewModel)
            observe(this@MainViewModel, Observer {
                it?.getContentIfNotHandled()?.let { sound ->
                    sounds.add(sound)
                    isSoundsDataChanges.postValue(Event(true))
                }
            })
        }
    }

    private fun playSound(context: Context, sound: Sound) {

        mediaPlayer = if (isDefayultSoundsContainsValue(sound.trackPath)) {
            MediaPlayer.create(context, DefaultSounds.valueOf(sound.trackPath).getTrakId())
        } else {
            MediaPlayer.create(context, Uri.parse(sound.trackPath))
        }

        mediaPlayer?.start()
    }

    private fun stopAndRecyclePlayer() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    fun onStartAudioRecordButtonClick() {

        currentRecordedSoundFile?.let {
            it.delete()
            currentRecordedSoundFile = null
        }

        val filePath: File? = try {
            // todo: add getting name from user
            createAudioFile(folder = getAppFolder("MySounds"))
        } catch (ex: IOException) {
            ex.printStackTrace()
            null
        }

        // Continue only if the File was successfully created
        filePath?.also {
            currentRecordedSoundFile = it
            startRecording(it.path)
        }

    }

    fun onStopAudioRecordButtonClick() {
        stopRecording()
    }

    fun onSaveRecordedAudioButtonClick() {
        currentRecordedSoundFile?.let {
            repository.saveSound(
                Sound(
                    null,
                    Uri.fromFile(currentRecordedSoundFile).path!!,
                    Uri.fromFile(currentRecordedSoundFile).lastPathSegment!!,
                    DefaultSounds.ANGRY_TIMLID.toString()
                )
            ).apply {
                removeObservers(this@MainViewModel)
                observe(this@MainViewModel, Observer {
                    it?.getContentIfNotHandled()?.let { sound ->
                        sounds.add(sound)
                        fragmentFinishEvent.postValue(Event(Unit))
                    }
                })
            }
        }
    }

    private var recorder: MediaRecorder? = null

    private fun startRecording(fileName: String) {
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setOutputFile(fileName)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

            try {
                prepare()
            } catch (e: IOException) {
                Log.e(TAG, "prepare() failed")
            }

            start()
        }
    }

    private fun stopRecording() {
        recorder?.apply {
            stop()
            release()
        }
        recorder = null
    }
}