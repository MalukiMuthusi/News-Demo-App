package codes.malukimuthusi.newsdemoapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import codes.malukimuthusi.newsdemoapp.ui.details.DetailsFragment

class Details : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, DetailsFragment.newInstance())
                .commitNow()
        }
    }
}
