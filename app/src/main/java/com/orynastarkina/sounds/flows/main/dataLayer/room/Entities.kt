package com.orynastarkina.sounds.flows.main.dataLayer.room

import android.arch.persistence.room.*

/**
 * Created by Oryna Starkina on 26.02.2019.
 */

@Entity(tableName = "tracks")
data class TrackEntity(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    @ColumnInfo(name = "path") var path: String = "",
    @ColumnInfo(name = "cover_path") var coverPath: String = "",
    @ColumnInfo(name = "description") var description: String = ""
)

@Entity(
    tableName = "dashboard",
    foreignKeys = [ForeignKey(
        entity = TrackEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("track_id"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class DashboardTrackEntity(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    @ColumnInfo(name = "track_id") val trackId: Long,
    @ColumnInfo(name = "position") var position: Int,
    @ColumnInfo(name = "is_header") var isHeader: Boolean
)

class DashboardTrackJoin {

    @Embedded
    var dashboardTrackEntity: DashboardTrackEntity? = null

    @Relation(parentColumn = "track_id", entityColumn = "id")
    var relatedTracks: List<TrackEntity>? = null
}