package com.example.liveapp2021


data class Gif(val gifId: Int, val type: String, val name: String, var selected: Boolean = false) {

}

data class Story(val storyId: Int, val name: String, val collection: String, val list: List<Int>) {

    val myDescription: String
        get() = "${name}, total ${list.size}, id=${storyId}"
}