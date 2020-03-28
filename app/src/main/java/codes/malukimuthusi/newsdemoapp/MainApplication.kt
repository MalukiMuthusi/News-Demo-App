package codes.malukimuthusi.newsdemoapp

import android.app.Application
import timber.log.Timber

class MainApplication : Application() {
    override fun onCreate() {

        /*
        * OnCreate is the first methode to be called when user taps to open the app.
        *
        * */
        super.onCreate()
        Timber.plant(Timber.DebugTree())

    }
}