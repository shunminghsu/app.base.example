package com.example.liveapp2021.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.liveapp2021.Gif
import com.example.liveapp2021.Story

@Entity(tableName = "story_table")
data class DatabaseStory constructor(
        @PrimaryKey(autoGenerate = true)
        val storyId: Int = 0,

        @ColumnInfo(name = "name_column")
        val name: String,
        @ColumnInfo(name = "group_column")
        val collection: String
)

@Entity(tableName = "gif_table")
data class DatabaseGif constructor(
        @PrimaryKey(autoGenerate = true)
        val gifId: Int = 0,

        @ColumnInfo(name = "type_column")
        val type: String,
        @ColumnInfo(name = "name_column")
        val name: String,
        @ColumnInfo(name = "group_column")
        val group: String
)


fun List<DatabaseGif>.asGifModel(): List<Gif> {
        return map {
                Gif(gifId = it.gifId, type = it.type, name = it.name)
        }
}

fun List<DatabaseStory>.asStoryModel(): List<Story> {
        return map {
                val _list = it.collection.trimEnd().split(" ").map {
                        it.toInt()
                }.toList()
                Story(storyId = it.storyId, name = it.name, collection = it.collection, list = _list)
        }
}