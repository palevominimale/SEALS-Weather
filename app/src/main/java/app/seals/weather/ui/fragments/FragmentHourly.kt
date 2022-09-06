package app.seals.weather.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.seals.weather.R
import app.seals.weather.ui.adapters.HourlyRecyclerAdapter
import app.seals.weather.ui.vm.SharedViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FragmentHourly : Fragment() {

    private val vm: SharedViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_hourly, container, false)
        val recycler = view.findViewById<RecyclerView>(R.id.hourlyRecycler)
        val swipe = view.findViewById<SwipeRefreshLayout>(R.id.swipeRefreshHourly)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = HourlyRecyclerAdapter(vm.forecastHourlyLive.value!!)

        swipe.setOnRefreshListener {
            vm.refresh()
        }

        vm.isRefreshingLive.observe(viewLifecycleOwner) {
            swipe.isRefreshing = it
        }

        vm.forecastHourlyLive.observe(viewLifecycleOwner) {
            recycler.adapter?.notifyDataSetChanged()
            swipe.isRefreshing = false
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        view!!.findViewById<RecyclerView>(R.id.hourlyRecycler).adapter?.notifyDataSetChanged()
    }
}