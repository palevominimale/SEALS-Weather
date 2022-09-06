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
import app.seals.weather.ui.adapters.DailyRecyclerAdapter
import app.seals.weather.ui.vm.SharedViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FragmentDaily : Fragment() {

    private val vm: SharedViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_daily, container, false)
        val recycler = view.findViewById<RecyclerView>(R.id.dailyRecycler)
        val swipe = view.findViewById<SwipeRefreshLayout>(R.id.swipeRefreshDaily)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = DailyRecyclerAdapter(vm.forecastDailyLive.value!!)

        swipe.setOnRefreshListener {
            vm.refresh()
        }

        vm.isRefreshingLive.observe(viewLifecycleOwner) {
            swipe.isRefreshing = it
        }

        vm.forecastDailyLive.observe(viewLifecycleOwner) {
            recycler.adapter?.notifyDataSetChanged()
            swipe.isRefreshing = false
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        view!!.findViewById<RecyclerView>(R.id.dailyRecycler).adapter?.notifyDataSetChanged()
    }
}