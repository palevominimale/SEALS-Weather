package app.seals.weather.domain.usecases.bootstrap

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import app.seals.weather.domain.interfaces.SettingsRepositoryInterface

class CheckPermissions(
    private val context: Context,
    private val activity: Activity,
    private val settingsRepository: SettingsRepositoryInterface
) {
    private val permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    private val granted = PackageManager.PERMISSION_GRANTED

    fun execute() {
        if (
            ActivityCompat.checkSelfPermission(context, permissions[0]) != granted
            && ActivityCompat.checkSelfPermission(context, permissions[1]) != granted
        ) {
            ActivityCompat.requestPermissions(activity, permissions,1)
            if (
                ActivityCompat.checkSelfPermission(context, permissions[0]) != granted
                && ActivityCompat.checkSelfPermission(context, permissions[1]) != granted
            ) {
                settingsRepository.setLocationAllowed()
            } else {
                settingsRepository.setLocationDisallowed()
            }
        }
    }
}