package com.orynastarkina.sounds.flows.main.dataLayer

import android.arch.lifecycle.MutableLiveData
import com.orynastarkina.sounds.base.IRepository
import com.orynastarkina.sounds.flows.main.dataLayer.utils.DashboardSound
import com.orynastarkina.sounds.flows.main.dataLayer.utils.Sound
import com.orynastarkina.sounds.utils.Event

/**
 * Created by Oryna Starkina on 25.02.2019.
 */
interface IMainRepository: IRepository {

    fun saveSound(sound: Sound) : MutableLiveData<Event<Sound>>

    fun getDashboardSounds(): MutableLiveData<List<DashboardSound>>

    fun getAllSounds(): MutableLiveData<List<Sound>>

    fun getNSounds(number: Int): MutableLiveData<List<Sound>>

    fun getMainSound(): MutableLiveData<DashboardSound>

    fun addSoundToDashboard(sound: DashboardSound)

    fun removeSoundFromDashboard(dashboardSound: DashboardSound) // todo: add remove by id
}