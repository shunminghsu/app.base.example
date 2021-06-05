package com.example.liveapp2021.home

import android.app.Application
import androidx.lifecycle.*
import com.example.liveapp2021.Story
import com.example.liveapp2021.database.DatabaseStory
import com.example.liveapp2021.database.MyDatabase
import com.example.liveapp2021.database.asStoryModel
import com.example.liveapp2021.repository.Repository
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repo: Repository = Repository(MyDatabase.getInstance(application))

    private val allDbStorys: LiveData<List<DatabaseStory>> = repo.allDbStorys

    val allStorys: LiveData<List<Story>> = Transformations.map(allDbStorys) {
        it.asStoryModel()
    }

    private val _navigateToSetting = MutableLiveData<Story?>()
    val navigateToSetting: LiveData<Story?>
        get() = _navigateToSetting

    fun doneNavigating() {
        _navigateToSetting.value = null
    }

    fun onStoryStart(story: Story) {
        _navigateToSetting.value = story
    }

    init {
        initBuildInStory()
        initAssetGif()
    }

    private fun initBuildInStory() {
        viewModelScope.launch {
            repo.buildInStory()
        }
    }

    private fun initAssetGif() {
        viewModelScope.launch {
            repo.checkAssetsToInsertGif()
        }
    }

    /**
     * Factory for constructing ViewModel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}