package com.accenture.roby

import android.app.Application
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.crashlytics.BuildConfig
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class JituApplication : Application() {
    private lateinit var analytics: FirebaseAnalytics

    override fun onCreate() {
        super.onCreate()
        analytics = Firebase.analytics
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(BuildConfig.DEBUG)
    }
}