package com.orynastarkina.sounds.flows.main.dataLayer.utils

import com.orynastarkina.sounds.flows.main.dataLayer.room.DashboardTrackEntity
import com.orynastarkina.sounds.flows.main.dataLayer.room.DashboardTrackJoin
import com.orynastarkina.sounds.flows.main.dataLayer.room.TrackEntity

/**
 * Created by Oryna Starkina on 25.02.2019.
 */

fun dbDashboardTrackToSoundConverter(dashboardTrack: DashboardTrackJoin?): DashboardSound {
    return DashboardSound(
        dashboardTrack?.dashboardTrackEntity?.id,
        Sound(
            dashboardTrack?.relatedTracks?.first()?.id,
            dashboardTrack?.relatedTracks?.first()?.path ?: "",
            dashboardTrack?.relatedTracks?.first()?.description ?: "",
            dashboardTrack?.relatedTracks?.first()?.coverPath ?: ""
        ),
        dashboardTrack?.dashboardTrackEntity?.position ?: -1,
        dashboardTrack?.dashboardTrackEntity?.isHeader ?: false
    )
}

fun dbTrackToSoundConverter(track: TrackEntity): Sound {
    return Sound(
        track.id,
        track.path,
        track.description,
        track.coverPath
    )
}

fun dbDashboardSoundToTrackConverter(dashboardSound: DashboardSound): DashboardTrackEntity {
    return DashboardTrackEntity(
        dashboardSound.id,
        dashboardSound.sound.id!!,
        dashboardSound.position,
        dashboardSound.isHeader
    )
}

fun dbSoundToTrackConverter(sound: Sound): TrackEntity {
    return TrackEntity(
        sound.id,
        sound.trackPath,
        sound.coverPath,
        sound.title
    )
}