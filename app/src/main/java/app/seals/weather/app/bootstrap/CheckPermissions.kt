package app.seals.weather.app.bootstrap

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

class CheckPermissions(
    private val context: Context,
    private val activity: Activity
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
//                val s = settingsRepository.get()
//                s.useLocation = true
//                settingsRepository.set(s)
            }
        }
    }
}