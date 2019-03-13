package com.orynastarkina.sounds.utils

import android.os.Environment
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Oryna Starkina on 26.02.2019.
 * todo   : need a review
 * source : https://medium.com/@tonyowen/room-entity-annotations-379150e1ca82
 */

open class SingletonHolder<out T, in A>(private var creator: ((A) -> T)?) {

    @Volatile
    private var instance: T? = null

    fun getInstance(arg: A): T {
        val i = instance
        if (i != null) {
            return i
        }

        return synchronized(this) {
            val i2 = instance
            if (i2 != null) {
                i2
            } else {
                val created = creator!!(arg)
                instance = created
                creator = null
                created
            }
        }
    }
}

fun getAppFolder(name: String) = File(
    Environment.getExternalStorageDirectory().toString()
            + "/" + name
)

@Throws(IOException::class)
fun createAudioFile(
    filePrefix: String = "Sound_${SimpleDateFormat(
        "yyyyMMdd_HHmmss",
        Locale.getDefault()
    ).format(Date())}_",
    fileExtention: String = ".3gp",
    folder: File
): File {

    if (!folder.exists()) {
        folder.mkdirs()
    }

    return File.createTempFile(
        filePrefix, /* prefix */
        fileExtention, /* suffix */
        folder /* directory */
    )
}