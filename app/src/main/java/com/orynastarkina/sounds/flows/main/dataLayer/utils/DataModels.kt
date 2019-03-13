package com.orynastarkina.sounds.flows.main.dataLayer.utils

import com.orynastarkina.sounds.R

/**
 * Created by Oryna Starkina on 25.02.2019.
 */

data class Sound(
    var id: Long? = null,
    val trackPath: String,
    val title: String,
    val coverPath: String
)

data class DashboardSound(
    var id: Long? = null,
    var sound: Sound,
    var position: Int,
    var isHeader: Boolean
)

data class MainSound(
    var trackPath: String,
    var coverPath: String
)

enum class DefaultSounds {
    X_FILES_SONG { // default main sound
        override fun getPosition() = 12

        override fun getTrakId() = R.raw.x_files_song

        override fun getCoverId() = R.drawable.logo

        override fun getDescriptionId() = R.string.x_files_song
    },

    ANGRY_TIMLID {
        override fun getPosition() = 0

        override fun getTrakId() = R.raw.angry_timlid

        override fun getCoverId() = R.drawable.round_logo

        override fun getDescriptionId() = R.string.angry_timlid
    },
    BADA_BUN_TSSS {
        override fun getPosition() = 1

        override fun getTrakId() = R.raw.bada_bum_tsss

        override fun getCoverId() = R.drawable.round_logo

        override fun getDescriptionId() = R.string.bada_bum_tsss
    },
    BZDYN {
        override fun getPosition() = 2

        override fun getTrakId() = R.raw.bzdyn

        override fun getCoverId() = R.drawable.round_logo

        override fun getDescriptionId() = R.string.bzdyn
    },
    DICH {
        override fun getPosition() = 3

        override fun getTrakId() = R.raw.dich

        override fun getCoverId() = R.drawable.round_logo

        override fun getDescriptionId() = R.string.dich
    },
    FAIL {
        override fun getPosition() = 4

        override fun getTrakId() = R.raw.fail

        override fun getCoverId() = R.drawable.round_logo

        override fun getDescriptionId() = R.string.fail
    },
    SMEH {
        override fun getPosition() = 5

        override fun getTrakId() = R.raw.smeh

        override fun getCoverId() = R.drawable.round_logo

        override fun getDescriptionId() = R.string.smeh
    },
    SMEH_IZ_SERIALOV {
        override fun getPosition() = 6

        override fun getTrakId() = R.raw.smeh_iz_serialov

        override fun getCoverId() = R.drawable.round_logo

        override fun getDescriptionId() = R.string.smeh_iz_serialov
    },
    SVIST {
        override fun getPosition() = 7

        override fun getTrakId() = R.raw.svist

        override fun getCoverId() = R.drawable.round_logo

        override fun getDescriptionId() = R.string.svist
    },
    VOT_ETO_POVOROT {
        override fun getPosition() = 8

        override fun getTrakId() = R.raw.vot_eto_povorot

        override fun getCoverId() = R.drawable.round_logo

        override fun getDescriptionId() = R.string.vot_eto_povorot
    },
    SYERBANYE {
        override fun getPosition() = 9

        override fun getTrakId() = R.raw.syerbanye

        override fun getCoverId() = R.drawable.round_logo

        override fun getDescriptionId() = R.string.syerbanye
    },
    OARTS {
        override fun getPosition() = 10

        override fun getTrakId() = R.raw.oarts

        override fun getCoverId() = R.drawable.round_logo

        override fun getDescriptionId() = R.string.oars
    },
    LASH {
        override fun getPosition() = 11

        override fun getTrakId() = R.raw.lash

        override fun getCoverId() = R.drawable.round_logo

        override fun getDescriptionId() = R.string.lash
    };

    abstract fun getTrakId(): Int
    abstract fun getCoverId(): Int
    abstract fun getDescriptionId(): Int
    abstract fun getPosition(): Int
}