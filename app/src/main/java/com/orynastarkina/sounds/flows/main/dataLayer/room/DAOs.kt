package com.orynastarkina.sounds.flows.main.dataLayer.room

import android.arch.persistence.room.*


/**
 * Created by Oryna Starkina on 26.02.2019.
 */

@Dao
interface TrackDao {

    @Query("SELECT * FROM tracks")
    fun getAllTracks(): List<TrackEntity>

    @Query("SELECT * FROM tracks WHERE id LIKE :id LIMIT 1")
    fun getSpecificTrack(id: Long): TrackEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(track: TrackEntity): Long

    @Update
    fun update(track: TrackEntity)

    @Delete
    fun delete(track: TrackEntity)
}

@Dao
interface DashboardDao {

    @Query("SELECT * FROM dashboard WHERE dashboard.is_header LIKE 0")
    fun getAllDashboardTrack(): List<DashboardTrackJoin>

    @Query("SELECT * FROM dashboard WHERE dashboard.is_header LIKE 1 LIMIT 1")
    fun getHeaderTrack(): DashboardTrackJoin?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dashboardTrack: DashboardTrackEntity): Long

    @Update
    fun update(dashboardTrack: DashboardTrackEntity)

    @Delete
    fun delete(dashboardTrack: DashboardTrackEntity)

}