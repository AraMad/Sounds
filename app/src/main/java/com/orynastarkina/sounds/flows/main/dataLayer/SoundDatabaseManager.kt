package com.orynastarkina.sounds.flows.main.dataLayer

import android.arch.persistence.room.Room
import android.content.Context
import com.orynastarkina.sounds.flows.main.dataLayer.room.DashboardTrackJoin
import com.orynastarkina.sounds.flows.main.dataLayer.room.SoundDatabase
import com.orynastarkina.sounds.flows.main.dataLayer.utils.*
import com.orynastarkina.sounds.utils.SOUND_DATABASE_NAME
import com.orynastarkina.sounds.utils.SingletonHolder


/**
 * Created by Oryna Starkina on 26.02.2019.
 */
class SoundDatabaseManager private constructor(context: Context) : IDatabase {

    companion object : SingletonHolder<SoundDatabaseManager, Context>(::SoundDatabaseManager)

    private val soundDatabase: SoundDatabase = Room.databaseBuilder(
        context,
        SoundDatabase::class.java,
        SOUND_DATABASE_NAME
    ).build()


    override fun saveSound(sound: Sound): Long = soundDatabase
        .trackDao()
        .insert(dbSoundToTrackConverter(sound))

    override fun getDashboardSounds(): List<DashboardSound> = soundDatabase
        .dashboardDao()
        .getAllDashboardTrack()
        .sortedBy { it.dashboardTrackEntity!!.position }
        .map(::dbDashboardTrackToSoundConverter)

    override fun getAllSounds(): List<Sound> = soundDatabase
        .trackDao()
        .getAllTracks()
        .map(::dbTrackToSoundConverter)


    // todo: implement
    override fun getNSounds(number: Int): List<Sound> = emptyList()

    override fun getMainSound(): DashboardSound = dbDashboardTrackToSoundConverter(
        soundDatabase
            .dashboardDao()
            .getHeaderTrack()
    )

    override fun addSoundToDashboard(dashboardSound: DashboardSound): Long = soundDatabase
        .dashboardDao()
        .insert(dbDashboardSoundToTrackConverter(dashboardSound))

    override fun removeSoundFromDashboard(dashboardSound: DashboardSound) = soundDatabase
        .dashboardDao()
        .delete(dbDashboardSoundToTrackConverter(dashboardSound))

}