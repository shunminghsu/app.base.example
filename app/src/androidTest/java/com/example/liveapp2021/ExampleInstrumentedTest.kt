package com.example.liveapp2021

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.liveapp2021.database.DatabaseGif
import com.example.liveapp2021.database.GifDao
import com.example.liveapp2021.database.MyDatabase
import com.example.liveapp2021.repository.Repository
import kotlinx.coroutines.runBlocking
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private lateinit var db: MyDatabase

    @Test
    fun test_database() = runBlocking {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val repo: Repository = Repository(MyDatabase.getInstance(appContext))
        db = MyDatabase.getInstance(appContext)
//        db.clearAllTables()
        db.gifDao.clear()

        repo.testInsertGif()

        val all = repo.getAllGif()
        assertEquals(all.size, 5)
        val big = repo.getAllGif("big")
        assertEquals(big.size, 3)
        val pink = repo.getAllGif("pink")
        assertEquals(pink.size, 2)
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.liveapp2021", appContext.packageName)
    }
}


class SleepDatabaseTest {

    private lateinit var gifDao: GifDao
    private lateinit var db: MyDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, MyDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        gifDao = db.gifDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetNight() {
//        val night = DatabaseGif(type = "assets", name = "big01.gif", group = "big")
//        gifDao.insert(night)
//
//        val check = gifDao.getLatest()
//        assertEquals(check?.group, "big")
//
//        val night2 = DatabaseGif(type = "assets", name = "pink01.gif", group = "pink")
//        gifDao.insert(night2)
//
//        val check2 = gifDao.getLatest()
//        assertEquals(check2?.group, "pink")

        gifDao.insertAll(getLocalData())

        val check = gifDao.getLatest()
        assertEquals(check?.group, "queen")

        val check3 = gifDao.getAllTest()
        assertEquals(check3.size, 4)


//        val tonight = sleepDao.getTonight()
//        assertEquals(tonight?.sleepQuality, -1)
    }

    private fun getLocalData(): List<DatabaseGif> {
        return listOf<DatabaseGif>(
            DatabaseGif(type = "assets", name = "big01.gif", group = "big"),
            DatabaseGif(type = "assets", name = "big02.gif", group = "big"),
            DatabaseGif(type = "assets", name = "big03.gif", group = "big"),
            DatabaseGif(type = "assets", name = "pink01.gif", group = "queen")
        )
    }
}