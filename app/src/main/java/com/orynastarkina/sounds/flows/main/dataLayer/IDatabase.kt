package com.orynastarkina.sounds.flows.main.dataLayer

import com.orynastarkina.sounds.flows.main.dataLayer.utils.DashboardSound
import com.orynastarkina.sounds.flows.main.dataLayer.utils.Sound

/**
 * Created by Oryna Starkina on 26.02.2019.
 */
interface IDatabase {

    fun saveSound(sound: Sound): Long

    fun getDashboardSounds(): List<DashboardSound>

    fun getAllSounds(): List<Sound>

    fun getNSounds(number: Int): List<Sound>

    fun getMainSound(): DashboardSound

    fun addSoundToDashboard(dashboardSound: DashboardSound): Long

    fun removeSoundFromDashboard(dashboardSound: DashboardSound)
}