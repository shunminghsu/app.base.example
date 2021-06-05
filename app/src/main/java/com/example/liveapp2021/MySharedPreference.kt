package com.example.liveapp2021

import android.content.Context
import android.content.SharedPreferences
import android.util.Log


class MySharedPreference {
    companion object {
        val TAG = "ShunMing"
        val SHARED_PREFS = "my_shared_prefs"
        val SHARED_PREFS_FIRST_TIME = "shared_prefs_ft"
    }
    var mIsFirst: Boolean = false
    lateinit var mContext: Context

    fun MySharedPreference(context: Context) {
        mContext = context.applicationContext
        Load()
    }

    fun Load() {
        Log.d(TAG, "Load preferences ")
        val sp = mContext.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        mIsFirst = sp.getBoolean(SHARED_PREFS_FIRST_TIME, true)
    }

    fun setFirstRun(context: Context) {
        val editor: SharedPreferences.Editor
            = context.getSharedPreferences(SHARED_PREFS, 0).edit()
        editor.putBoolean(SHARED_PREFS_FIRST_TIME, false)
        editor.commit()
    }

    fun isFirst(): Boolean {
        return mIsFirst
    }
}