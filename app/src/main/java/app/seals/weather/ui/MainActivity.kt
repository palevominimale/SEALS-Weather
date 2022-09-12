package app.seals.weather.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import app.seals.weather.R
import app.seals.weather.domain.usecases.bootstrap.CheckData
import app.seals.weather.domain.usecases.bootstrap.CheckPermissions
import app.seals.weather.domain.interfaces.ForecastRepositoryDAO
import app.seals.weather.domain.interfaces.SettingsRepositoryInterface
import app.seals.weather.domain.usecases.forecast.RefreshForecast
import app.seals.weather.ui.adapters.MainSectionPagerAdapter
import app.seals.weather.ui.fragments.FragmentSettingsDialog
import com.google.android.material.tabs.TabLayout
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val forecastRepository by inject<ForecastRepositoryDAO>()
        val retrofitNetworkRefresh by inject<RefreshForecast>()
        val settingsRepository by inject<SettingsRepositoryInterface>()

        val checkPermissions = CheckPermissions(
            context = applicationContext,
            activity = this,
            settingsRepository = settingsRepository)

        checkPermissions.execute()
        setContentView(R.layout.activity_main)
        CheckData(forecastRepository, retrofitNetworkRefresh, settingsRepository).execute()

        val sectionsPagerAdapter = MainSectionPagerAdapter(this, supportFragmentManager )
        val viewPager = findViewById<ViewPager>(R.id.viewPager).apply {
            adapter = sectionsPagerAdapter
        }
        findViewById<TabLayout>(R.id.tabs).setupWithViewPager(viewPager)

        val dialogFragment = FragmentSettingsDialog()
        findViewById<ImageView>(R.id.settingsImageView).setOnClickListener {
            dialogFragment.show(supportFragmentManager, "")
        }

    }
}