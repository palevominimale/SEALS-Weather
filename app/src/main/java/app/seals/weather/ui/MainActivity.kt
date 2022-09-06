package app.seals.weather.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import app.seals.weather.R
import app.seals.weather.app.bootstrap.CheckData
import app.seals.weather.app.bootstrap.CheckPermissions
import app.seals.weather.app.location.GetLocation
import app.seals.weather.data.room.ForecastRepositoryDAO
import app.seals.weather.domain.interfaces.NetworkApiInterface
import app.seals.weather.ui.adapters.MainSectionPagerAdapter
import com.google.android.material.tabs.TabLayout
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val forecastRepository by inject<ForecastRepositoryDAO>()
        val network by inject<NetworkApiInterface>()
        val getLocation by inject<GetLocation>()

        val checkPermissions = CheckPermissions(context = applicationContext, activity = this)

        checkPermissions.execute()
        setContentView(R.layout.activity_main)
        getLocation.execute()
        CheckData(forecastRepository, network).execute()
        val sectionsPagerAdapter = MainSectionPagerAdapter(this, supportFragmentManager )
        val viewPager = findViewById<ViewPager>(R.id.viewPager).apply {
            adapter = sectionsPagerAdapter
        }
        findViewById<TabLayout>(R.id.tabs).setupWithViewPager(viewPager)
    }
}