package com.example.liveapp2021.setting

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.liveapp2021.Gif
import com.example.liveapp2021.database.DatabaseGif
import com.example.liveapp2021.database.MyDatabase
import com.example.liveapp2021.repository.Repository
import kotlinx.coroutines.launch

class SettingViewModel(storyId: Int, application: Application) : AndroidViewModel(application) {
    private val repo: Repository = Repository(MyDatabase.getInstance(application))

    val listDbGifs: LiveData<List<DatabaseGif>> = repo.settingListDbGifs
    val listGifs = MutableLiveData<MutableList<Gif>>(mutableListOf<Gif>())

    val _selectedAGif = MutableLiveData<Int?>()
    val selectedAGif: LiveData<Int?>
        get() = _selectedAGif

    fun doneSelected() {
        _selectedAGif.value = null
    }

    private val _navigateToPlay = MutableLiveData<Boolean?>()
    val navigateToPlay: LiveData<Boolean?>
        get() = _navigateToPlay

    fun doneNavigating() {
        _navigateToPlay.value = null
    }

    fun onStart() {
        _navigateToPlay.value = true
    }


    init {
        getGifList(storyId)
    }

    private fun getGifList(id: Int) {
        viewModelScope.launch {
            repo.getGifByStoryId(id)
        }
    }

    fun transformModelToGif() {
        listGifs.value?.clear()
        val temp = mutableListOf<Gif>()
        listDbGifs.value?.forEach {
            temp.add(Gif(gifId = it.gifId, name = it.name, type = it.type))
        }
        listGifs.value = temp
    }

    fun selectAGif(id: Int) {
        _selectedAGif.value = id

        listGifs.value?.forEach {
            it.selected = it.gifId == id
        }
    }

    fun onFirst() {

    }

    fun onNext() {

    }

    fun onLast() {

    }

    /**
     * Factory for constructing ViewModel with parameter
     */
    class Factory(val storyId: Int, val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SettingViewModel(storyId, app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}