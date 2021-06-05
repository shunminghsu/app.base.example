package com.example.liveapp2021.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.liveapp2021.database.DatabaseGif
import com.example.liveapp2021.database.DatabaseStory
import com.example.liveapp2021.database.MyDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class Repository(private val database: MyDatabase) {

    var settingListDbGifs = MutableLiveData<List<DatabaseGif>>()

    var allDbGifs: LiveData<List<DatabaseGif>> = database.gifDao.getAll()
    var allDbStorys: LiveData<List<DatabaseStory>> = database.storyDao.getAll()

    suspend fun getGifByStoryId(storyId: Int) {
        withContext(Dispatchers.IO) {
            val _list = mutableListOf<DatabaseGif>()
            val collection: String = database.storyDao.getStory(storyId).collection
            collection.trimEnd().split(" ").forEach {
                gifId -> _list.add(database.gifDao.getGif(gifId.toInt()))
            }
            settingListDbGifs.postValue(_list)
//            val _list = collection.trimEnd().split(" ").map {
//                    gifId -> database.gifDao.getGif(gifId.toInt())
//            }.toList()
//            gifList.addAll(_list)
        }
    }

    suspend fun checkAssetsToInsertGif() {
        withContext(Dispatchers.IO) {
            val assetGifs = getAssetsGifData()
            database.gifDao.insertAll(assetGifs)
        }
    }

    suspend fun buildInStory() {
        withContext(Dispatchers.IO) {
            val story = getBuildInStoryData()
            database.storyDao.insertAll(story)
        }
    }

    private fun getAssetsGifData(): List<DatabaseGif> {
        return listOf<DatabaseGif>(
            DatabaseGif(gifId = 1, type = "assets", name = "big01.gif", group = "big"),
            DatabaseGif(gifId = 2, type = "assets", name = "big02.gif", group = "big"),
            DatabaseGif(gifId = 3, type = "assets", name = "big03.gif", group = "big"),
            DatabaseGif(gifId = 4, type = "assets", name = "big04.gif", group = "big"),
            DatabaseGif(gifId = 5, type = "assets", name = "big05.gif", group = "big")
        )
    }

    private fun getBuildInStoryData(): List<DatabaseStory> {
        return listOf<DatabaseStory>(
            DatabaseStory(storyId = 1, name = "story1", collection = "1 2 3 4 5 "),
            DatabaseStory(storyId = 2, name = "story1", collection = "3 5 ")
        )
    }

    suspend fun refreshGifs() {
//        withContext(Dispatchers.IO) {
//            val assetGifs = listOf<DatabaseGif>(
//                DatabaseGif(type = "assets", name = "big01.gif", group = "big")
//            )
//            database.gifDao.insertAll(assetGifs)
//        }
    }

    fun getGifSize(): Int {
        return allDbGifs.value?.size ?: -1
    }

    fun getAll(): LiveData<List<DatabaseGif> >{
        return database.gifDao.getAll()
    }

    suspend fun getAllGif(): List<DatabaseGif> {
        return database.gifDao.getAllTest()
    }

    suspend fun getAllGif(group: String): List<DatabaseGif> {
        return database.gifDao.getGroupGifsTest(group)
    }

    suspend fun testInsertGif() {
        withContext(Dispatchers.IO) {
            val assetGifs = listOf<DatabaseGif>(
                DatabaseGif(gifId = 1, type = "assets", name = "big01.gif", group = "big"),
                DatabaseGif(gifId = 2, type = "assets", name = "big02.gif", group = "big"),
                DatabaseGif(gifId = 3, type = "assets", name = "pink01.gif", group = "pink"),
                DatabaseGif(gifId = 4, type = "assets", name = "big04.gif", group = "big"),
                DatabaseGif(gifId = 5, type = "assets", name = "pink02.gif", group = "pink")
            )
            database.gifDao.insertAll(assetGifs)
        }
    }

//    suspend fun refreshVideos() {
//        withContext(Dispatchers.IO) {
//            Timber.d("refresh videos is called");
//            val playlist = DevByteNetwork.devbytes.getPlaylist()
//            database.videoDao.insertAll(playlist.asDatabaseModel())
//        }
//    }

}
