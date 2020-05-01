package codes.malukimuthusi.newsdemoapp

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class MainApplication : Application() {
    private val applicationScope = CoroutineScope(Dispatchers.Default)
    private fun delayedInit() {
        applicationScope.launch {
            Timber.plant(Timber.DebugTree())
//            refreshNewsArticles(this@MainApplication)
        }
    }

    override fun onCreate() {
        delayedInit()

        super.onCreate()


    }


}