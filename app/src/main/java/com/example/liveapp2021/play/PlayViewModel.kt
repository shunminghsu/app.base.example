package com.example.liveapp2021.play

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlayViewModel : ViewModel() {

    private val _navigateToSetting = MutableLiveData<Boolean?>()
    val navigateToSetting: LiveData<Boolean?>
        get() = _navigateToSetting

    fun doneNavigating() {
        _navigateToSetting.value = null
    }

    fun onClose() {
        _navigateToSetting.value = true
    }

    fun onNext() {

    }

    fun onFirst() {

    }

    fun onLast() {

    }

    fun onAutoChecked() {

    }

    fun onRandomChecked() {

    }
}