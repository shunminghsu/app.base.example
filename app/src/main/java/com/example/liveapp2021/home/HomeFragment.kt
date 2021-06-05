package com.example.liveapp2021.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.liveapp2021.R
import com.example.liveapp2021.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentHomeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        val activity = requireNotNull(this.activity)
        val viewModel: HomeViewModel = ViewModelProvider(this,
            HomeViewModel.Factory(activity.application)).get(HomeViewModel::class.java)

        binding.homeViewModel = viewModel
        binding.setLifecycleOwner(this)

        viewModel.navigateToSetting.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                this.findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToSettingFragment(it.storyId.toLong()))

                viewModel.doneNavigating()
            }
        })

        val adapter = HomeStoryAdapter(HomeStoryListener { story ->
            viewModel.onStoryStart(story)
        })
        binding.storyList.adapter = adapter
        binding.storyList.addItemDecoration(DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL))
        viewModel.allStorys.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
        val manager = LinearLayoutManager(activity)
        binding.storyList.layoutManager = manager

        return binding.root
    }

}