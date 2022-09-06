package app.seals.weather.ui.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import app.seals.weather.R
import app.seals.weather.ui.fragments.FragmentDaily
import app.seals.weather.ui.fragments.FragmentHourly

private val TAB_TITLES = arrayOf(
    R.string.tab1,
    R.string.tab2,
)

@Suppress("DEPRECATION")
class MainSectionPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    private val items = arrayOf(
        FragmentHourly(),
        FragmentDaily()
    )

    override fun getItem(position: Int): Fragment {
        return items[position]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return items.size
    }
}