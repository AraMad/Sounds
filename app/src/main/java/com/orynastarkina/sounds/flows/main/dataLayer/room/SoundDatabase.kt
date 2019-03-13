package com.orynastarkina.sounds.flows.main.dataLayer.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.orynastarkina.sounds.flows.main.dataLayer.room.DashboardDao
import com.orynastarkina.sounds.flows.main.dataLayer.room.DashboardTrackEntity
import com.orynastarkina.sounds.flows.main.dataLayer.room.TrackDao
import com.orynastarkina.sounds.flows.main.dataLayer.room.TrackEntity

/**
 * Created by Oryna Starkina on 26.02.2019.
 */
@Database(
    entities = [TrackEntity::class,
        DashboardTrackEntity::class], version = 1
)
abstract class SoundDatabase : RoomDatabase() {

    abstract fun trackDao(): TrackDao

    abstract fun dashboardDao(): DashboardDao
}