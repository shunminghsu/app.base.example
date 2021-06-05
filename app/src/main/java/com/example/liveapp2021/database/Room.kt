package com.example.liveapp2021.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import org.jetbrains.annotations.NotNull

@Dao
interface StoryDao {
    @Query("SELECT * FROM story_table ORDER BY storyId")
    fun getAll(): LiveData<List<DatabaseStory>>

    @Query("SELECT * FROM story_table ORDER BY storyId")
    fun getAllTest(): List<DatabaseStory>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: DatabaseStory)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( items: List<DatabaseStory>)

    @NotNull
    @Query("SELECT * FROM story_table WHERE storyId = :id")
    fun getStory(id: Int): DatabaseStory

    @Query("DELETE FROM story_table")
    suspend fun clear()
}

@Dao
interface GifDao {
    @Query("SELECT * FROM gif_table ORDER BY gifId")
    fun getAll(): LiveData<List<DatabaseGif>>

    @Query("select * from gif_table where group_column = :group")
    fun getGroupGifs(group: String): LiveData<List<DatabaseGif>>

    @Query("SELECT * FROM gif_table ORDER BY gifId")
    fun getAllTest(): List<DatabaseGif>

    @Query("select * from gif_table where group_column = :group")
    fun getGroupGifsTest(group: String): List<DatabaseGif>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: DatabaseGif)

    @NotNull
    @Query("SELECT * FROM gif_table WHERE gifId = :id")
    fun getGif(id: Int): DatabaseGif

    @Query("SELECT * FROM gif_table ORDER BY gifId DESC LIMIT 1")
    fun getLatest(): DatabaseGif?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( items: List<DatabaseGif>)

    @Query("DELETE FROM gif_table")
    suspend fun clear()
}



@Database(entities = [DatabaseGif::class, DatabaseStory::class], version = 1)
abstract class MyDatabase: RoomDatabase() {
    abstract val gifDao: GifDao
    abstract val storyDao: StoryDao

    companion object {
        @Volatile
        private var INSTANCE: MyDatabase? = null
        fun getInstance(context: Context): MyDatabase {
            // Multiple threads can ask for the database at the same time, ensure we only initialize
            // it once by using synchronized. Only one thread may enter a synchronized block at a
            // time.
            synchronized(this) {

                // Copy the current value of INSTANCE to a local variable so Kotlin can smart cast.
                // Smart cast is only available to local variables.
                var instance = INSTANCE

                // If instance is `null` make a new database instance.
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MyDatabase::class.java,
                        "my_database"
                    )
                        // Wipes and rebuilds instead of migrating if no Migration object.
                        // Migration is not part of this lesson. You can learn more about
                        // migration with Room in this blog post:
                        // https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929
                        .fallbackToDestructiveMigration()
                        .build()
                    // Assign INSTANCE to the newly created database.
                    INSTANCE = instance
                }

                // Return instance; smart cast to be non-null.
                return instance
            }
        }

    }
}

