package com.example.domain.utils

import android.util.Log
import javax.inject.Inject

interface Logger {
    fun logError(tag: String, message: String)

    fun logInfo(tag: String, message: String)
}

class AndroidLogger @Inject constructor() : Logger {
    override fun logError(tag: String, message: String) {
        Log.e(tag, message)
    }

    override fun logInfo(tag: String, message: String) {
        Log.i(tag, message)
    }
}