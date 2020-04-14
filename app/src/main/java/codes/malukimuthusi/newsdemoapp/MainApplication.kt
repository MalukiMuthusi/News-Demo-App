package codes.malukimuthusi.newsdemoapp

import android.app.Application
import androidx.work.*
import codes.malukimuthusi.newsdemoapp.workManager.ApiWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit

class MainApplication : Application() {
    private val applicationScope = CoroutineScope(Dispatchers.Default)
    private fun delayedInit() {
        applicationScope.launch {
            /*
       * OnCreate is the first methode to be called when user taps to open the app.
       *
       * */
            Timber.plant(Timber.DebugTree())
            fetchArticles()
        }
    }

    override fun onCreate() {
        delayedInit()

        delayedInit()
        super.onCreate()


    }

    /*
    * Fetch News Articles Periodicaly.
    *
    * */
    private fun fetchArticles() {
        /*
        * Constraints for fetching News Articles.
        *  Must be connected to the network.
        *
        * */
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        /*
        * Build Work Request.
        *   With Constraints.
        *
        * */
        val repeatingWorkRequest =
            PeriodicWorkRequestBuilder<ApiWorker>(16, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build()

        /*
        * Create Work Manager.
        *
        *   Request Work.
        *
        * */
        Timber.d("Periodic Work request for sync is scheduled")
        WorkManager.getInstance().enqueueUniquePeriodicWork(
            ApiWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingWorkRequest
        )


    }


}