package com.udacity.asteroidradar

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.*
import com.udacity.asteroidradar.work.RefreshDataWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import timber.log.Timber.Forest.plant
import java.util.concurrent.TimeUnit


class AsteroidApplication : Application() {
    private val applicationScope = CoroutineScope(Dispatchers.Default)
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            plant(Timber.DebugTree())
            delayInit()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun delayInit() {
        applicationScope.launch {

            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .setRequiresBatteryNotLow(true).setRequiresCharging(true).setRequiresDeviceIdle(true)
                .apply {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        setRequiresDeviceIdle(true)
                    }
                }.build()
            val repeatingRequest =
                PeriodicWorkRequestBuilder<RefreshDataWorker>(1, TimeUnit.DAYS).setConstraints(
                    constraints
                ).build()
            WorkManager.getInstance().enqueueUniquePeriodicWork(
                RefreshDataWorker.NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                repeatingRequest
            )

            val loadAsteroidRequest = OneTimeWorkRequestBuilder<RefreshDataWorker>().build()
            WorkManager.getInstance().enqueue(loadAsteroidRequest)
        }
    }
}