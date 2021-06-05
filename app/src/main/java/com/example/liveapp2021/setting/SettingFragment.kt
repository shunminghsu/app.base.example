package com.example.liveapp2021.setting

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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.liveapp2021.R
import com.example.liveapp2021.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSettingBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)

        val safeArgs: SettingFragmentArgs by navArgs()
        val activity = requireNotNull(this.activity)
        val viewModel: SettingViewModel = ViewModelProvider(this,
            SettingViewModel.Factory(safeArgs.storyId.toInt(), activity.application)).get(SettingViewModel::class.java)

        binding.settingViewModel = viewModel
        binding.setLifecycleOwner(this)

        viewModel.navigateToPlay.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController().navigate(
                    SettingFragmentDirections.actionSettingFragmentToPlayFragment())

                viewModel.doneNavigating()
            }
        })

        val adapter = SettingGifAdapter(SettingGifListener { gif ->
            Log.d("Shun", "click" + gif.gifId)
            viewModel.selectAGif(gif.gifId)
        })
        binding.gifList.adapter = adapter
        viewModel.listDbGifs.observe(viewLifecycleOwner, Observer {
            Log.d("Shun", "listDbGifs.observe")
            viewModel.transformModelToGif()
        })
        viewModel.listGifs.observe(viewLifecycleOwner, Observer {
            Log.d("Shun", "listGifs.observe")
            it?.let {
                adapter.data = it
            }
        })
        viewModel.selectedAGif.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                //viewModel.showAllGifInfo()
                adapter.notifyDataSetChanged()
                viewModel.doneSelected()
            }
        })
        val manager = LinearLayoutManager(activity)
        binding.gifList.layoutManager = manager

        return binding.root
    }

}