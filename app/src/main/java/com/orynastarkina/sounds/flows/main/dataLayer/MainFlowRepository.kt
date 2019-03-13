package com.orynastarkina.sounds.flows.main.dataLayer

import android.arch.lifecycle.MutableLiveData
import com.orynastarkina.sounds.flows.main.dataLayer.utils.DashboardSound
import com.orynastarkina.sounds.flows.main.dataLayer.utils.DefaultSounds
import com.orynastarkina.sounds.flows.main.dataLayer.utils.Sound
import com.orynastarkina.sounds.utils.Event
import org.jetbrains.anko.doAsync

/**
 * Created by Oryna Starkina on 25.02.2019.
 */
class MainFlowRepository(private val database: IDatabase) : IMainRepository {

    override val TAG: String
        get() = this.javaClass.simpleName


    private val sounds = MutableLiveData<List<Sound>>()
    private val dashboardSounds = MutableLiveData<List<DashboardSound>>()
    private val currentNSounds = MutableLiveData<List<Sound>>()
    private val mainSound = MutableLiveData<DashboardSound>()

    private val newSound = MutableLiveData<Event<Sound>>()

    private val defaultMainSound by lazy {
        DashboardSound(
            null,
            Sound(
                null,
                DefaultSounds.X_FILES_SONG.toString(),
                DefaultSounds.X_FILES_SONG.toString(),
                DefaultSounds.X_FILES_SONG.toString()
            ),
            DefaultSounds.X_FILES_SONG.getPosition(),
            true
        )
    }
    private val defaultDashboard by lazy {
        arrayListOf(
            DashboardSound(
                null,
                Sound(
                    null,
                    DefaultSounds.BADA_BUN_TSSS.toString(),
                    DefaultSounds.BADA_BUN_TSSS.toString(),
                    DefaultSounds.BADA_BUN_TSSS.toString()
                ),
                DefaultSounds.BADA_BUN_TSSS.getPosition(),
                false
            ),
            DashboardSound(
                null,
                Sound(
                    null,
                    DefaultSounds.ANGRY_TIMLID.toString(),
                    DefaultSounds.ANGRY_TIMLID.toString(),
                    DefaultSounds.ANGRY_TIMLID.toString()
                ),
                DefaultSounds.ANGRY_TIMLID.getPosition(),
                false
            ),
            DashboardSound(
                null,
                Sound(
                    null,
                    DefaultSounds.BZDYN.toString(),
                    DefaultSounds.BZDYN.toString(),
                    DefaultSounds.BZDYN.toString()
                ),
                DefaultSounds.BZDYN.getPosition(),
                false
            ),
            DashboardSound(
                null,
                Sound(
                    null,
                    DefaultSounds.DICH.toString(),
                    DefaultSounds.DICH.toString(),
                    DefaultSounds.DICH.toString()
                ),
                DefaultSounds.DICH.getPosition(),
                false
            ),
            DashboardSound(
                null,
                Sound(
                    null,
                    DefaultSounds.FAIL.toString(),
                    DefaultSounds.FAIL.toString(),
                    DefaultSounds.FAIL.toString()
                ),
                DefaultSounds.FAIL.getPosition(),
                false
            ),
            DashboardSound(
                null,
                Sound(
                    null,
                    DefaultSounds.SMEH.toString(),
                    DefaultSounds.SMEH.toString(),
                    DefaultSounds.SMEH.toString()
                ),
                DefaultSounds.SMEH.getPosition(),
                false
            ),
            DashboardSound(
                null,
                Sound(
                    null,
                    DefaultSounds.SMEH_IZ_SERIALOV.toString(),
                    DefaultSounds.SMEH_IZ_SERIALOV.toString(),
                    DefaultSounds.SMEH_IZ_SERIALOV.toString()
                ),
                DefaultSounds.SMEH_IZ_SERIALOV.getPosition(),
                false
            ),
            DashboardSound(
                null,
                Sound(
                    null,
                    DefaultSounds.SVIST.toString(),
                    DefaultSounds.SVIST.toString(),
                    DefaultSounds.SVIST.toString()
                ),
                DefaultSounds.SVIST.getPosition(),
                false
            ),
            DashboardSound(
                null,
                Sound(
                    null,
                    DefaultSounds.VOT_ETO_POVOROT.toString(),
                    DefaultSounds.VOT_ETO_POVOROT.toString(),
                    DefaultSounds.VOT_ETO_POVOROT.toString()
                ),
                DefaultSounds.VOT_ETO_POVOROT.getPosition(),
                false
            ),
            DashboardSound(
                null,
                Sound(
                    null,
                    DefaultSounds.SYERBANYE.toString(),
                    DefaultSounds.SYERBANYE.toString(),
                    DefaultSounds.SYERBANYE.toString()
                ),
                DefaultSounds.SYERBANYE.getPosition(),
                false
            ),
            DashboardSound(
                null,
                Sound(
                    null,
                    DefaultSounds.OARTS.toString(),
                    DefaultSounds.OARTS.toString(),
                    DefaultSounds.OARTS.toString()
                ),
                DefaultSounds.OARTS.getPosition(),
                false
            ),
            DashboardSound(
                null,
                Sound(
                    null,
                    DefaultSounds.LASH.toString(),
                    DefaultSounds.LASH.toString(),
                    DefaultSounds.LASH.toString()
                ),
                DefaultSounds.LASH.getPosition(),
                false
            )
        )
    }

    override fun saveSound(sound: Sound): MutableLiveData<Event<Sound>> {
        doAsync {
            sound.id = database.saveSound(sound)
            newSound.postValue(Event(sound))
        }

        return newSound
    }

    override fun getDashboardSounds(): MutableLiveData<List<DashboardSound>> {

        doAsync {
            val dbDashboardSounds = database.getDashboardSounds()
            if (dbDashboardSounds.isEmpty()) {
                for (i in 0 until defaultDashboard.size) {
                    defaultDashboard[i].sound.id = database.saveSound(defaultDashboard[i].sound)
                    defaultDashboard[i].id = database.addSoundToDashboard(defaultDashboard[i])
                }
                dashboardSounds.postValue(defaultDashboard.sortedBy { it.position })
            } else {
                dashboardSounds.postValue(dbDashboardSounds)
            }
        }

        return dashboardSounds
    }

    override fun getAllSounds(): MutableLiveData<List<Sound>> {

        doAsync {
            val dbSounds = database.getAllSounds()
            if (dbSounds.isEmpty()) {

                val sList = ArrayList<Sound>()
                for (dSound in defaultDashboard) {
                    val sound = Sound(
                        database.saveSound(dSound.sound),
                        dSound.sound.trackPath,
                        dSound.sound.coverPath,
                        dSound.sound.coverPath
                    )
                    sList.add(sound)
                }

                sounds.postValue(sList)
            } else {
                sounds.postValue(dbSounds)
            }
        }

        return sounds
    }

    // todo: implement
    override fun getNSounds(number: Int): MutableLiveData<List<Sound>> {
        return currentNSounds
    }

    override fun getMainSound(): MutableLiveData<DashboardSound> {

        doAsync {
            val dbMainTrack = database.getMainSound()

            if (dbMainTrack.id == null) {

                defaultMainSound.sound.id = database.saveSound(defaultMainSound.sound)
                defaultMainSound.id = database.addSoundToDashboard(defaultMainSound)

                mainSound.postValue(defaultMainSound)
            } else {
                mainSound.postValue(dbMainTrack)
            }
        }

        return mainSound
    }

    override fun addSoundToDashboard(sound: DashboardSound) {
        doAsync {
            database.addSoundToDashboard(sound)
        }
    }

    override fun removeSoundFromDashboard(dashboardSound: DashboardSound) {
        doAsync {
            database.removeSoundFromDashboard(dashboardSound)
        }
    }
}