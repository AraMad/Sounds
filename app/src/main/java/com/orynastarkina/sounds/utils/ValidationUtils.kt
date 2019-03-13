package com.orynastarkina.sounds.utils

import com.orynastarkina.sounds.flows.main.dataLayer.utils.DefaultSounds

/**
 * Created by Oryna Starkina on 06.03.2019.
 */

fun isDefayultSoundsContainsValue(value: String): Boolean {
    for (sound in DefaultSounds.values()) {
        if (sound.toString() == value) {
            return true
        }
    }

    return false
}