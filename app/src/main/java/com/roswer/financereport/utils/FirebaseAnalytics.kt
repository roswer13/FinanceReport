package com.roswer.financereport.utils

import android.os.Bundle
import android.util.Log
import androidx.annotation.Keep
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.BuildConfig
import com.google.firebase.ktx.Firebase

@Keep
object FirebaseAnalyticsUtil {

    private val firebaseAnalytics: FirebaseAnalytics by lazy {
        Firebase.analytics
    }

    fun logEvent(eventName: String) {
        if (eventName.isEmpty()) return
        if (BuildConfig.DEBUG) {
            Log.d("FirebaseAnalyticsUtil", "Logging event: $eventName")
        }
        firebaseAnalytics.logEvent(eventName, null)
    }

    fun logEvent(eventName: String, params: Bundle) {
        if (eventName.isEmpty()) return
        if (BuildConfig.DEBUG) {
            Log.d("FirebaseAnalyticsUtil", "Logging event: $eventName, params: $params")
        }
        firebaseAnalytics.logEvent(eventName, params)
    }

    fun setUserId(userId: String) {
        firebaseAnalytics.setUserId(userId)
    }

    fun setUserProperty(propertyName: String, propertyValue: String) {
        firebaseAnalytics.setUserProperty(propertyName, propertyValue)
    }
}