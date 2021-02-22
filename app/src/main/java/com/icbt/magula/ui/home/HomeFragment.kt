package com.icbt.magula.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.icbt.magula.R
import com.icbt.magula.data.db.AppDatabase
import com.icbt.magula.data.network.MyApi
import com.icbt.magula.data.network.ServiceResponse
import com.icbt.magula.data.repository.UserRepository
import com.icbt.magula.databinding.FragmentHomeBinding
import com.icbt.magula.ui.adapter.HomeRecyclerViewAdapter
import com.icbt.magula.ui.listner.AuthListner
import com.icbt.magula.ui.utils.toast

class HomeFragment : Fragment(), AuthListner {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding:FragmentHomeBinding
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val api = MyApi();
        val db = AppDatabase(requireContext())
        val repository = UserRepository(api,db)
        val factory = HomeViewModelFactory(repository,viewLifecycleOwner,requireActivity(),requireContext())

        viewModel = ViewModelProviders.of(this,factory).get(HomeViewModel::class.java)
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            homeViewModel = viewModel
        }
        viewModel.authListner = this

//        var hotels: List<ServiceResponse>? = null
//
        recyclerView = view.findViewById(R.id.home_recycler_view)
//        recyclerView.adapter = hotels?.let { HomeRecyclerViewAdapter(it) }
//        recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.getServices()

        viewModel.services.observe(viewLifecycleOwner, Observer { service ->
            recyclerView.also {
                it.layoutManager = LinearLayoutManager(requireContext())
                it.setHasFixedSize(true)
                it.adapter = HomeRecyclerViewAdapter(service)
            }
        })


    }

    override fun onCreate() {
    }

    override fun onSuccess(message: String) {
        context?.toast(message)
    }

    override fun onFailure(message: String) {
        context?.toast(message)
    }
}