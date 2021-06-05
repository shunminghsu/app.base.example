package com.example.liveapp2021.play

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.liveapp2021.R
import com.example.liveapp2021.databinding.FragmentPlayBinding


class PlayFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentPlayBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_play, container, false)

        val viewModel = ViewModelProvider(this).get(PlayViewModel::class.java)

        binding.playViewModel = viewModel
        binding.setLifecycleOwner(this)

        viewModel.navigateToSetting.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController().popBackStack()
                viewModel.doneNavigating()
            }
        })
        return binding.root
    }

}