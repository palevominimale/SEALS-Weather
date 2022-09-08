package app.seals.weather.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import app.seals.weather.R
import app.seals.weather.domain.usecases.bootstrap.CheckData
import app.seals.weather.domain.usecases.bootstrap.CheckPermissions
import app.seals.weather.domain.usecases.location.UpdateLocation
import app.seals.weather.domain.interfaces.ForecastRepositoryDAO
import app.seals.weather.domain.interfaces.SettingsRepositoryInterface
import app.seals.weather.domain.usecases.forecast.RefreshForecast
import app.seals.weather.ui.adapters.MainSectionPagerAdapter
import com.google.android.material.tabs.TabLayout
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val forecastRepository by inject<ForecastRepositoryDAO>()
        val retrofitNetworkRefresh by inject<RefreshForecast>()
        val updateLocation by inject<UpdateLocation>()
        val settingsRepository by inject<SettingsRepositoryInterface>()

        val checkPermissions = CheckPermissions(
            context = applicationContext,
            activity = this,
            settingsRepository = settingsRepository)

        checkPermissions.execute()
        setContentView(R.layout.activity_main)
        updateLocation.execute()
        CheckData(forecastRepository, retrofitNetworkRefresh).execute()
        val sectionsPagerAdapter = MainSectionPagerAdapter(this, supportFragmentManager )
        val viewPager = findViewById<ViewPager>(R.id.viewPager).apply {
            adapter = sectionsPagerAdapter
        }
        findViewById<TabLayout>(R.id.tabs).setupWithViewPager(viewPager)
    }
}