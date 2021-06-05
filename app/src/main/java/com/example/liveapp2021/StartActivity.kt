package com.example.liveapp2021

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class StartActivity : AppCompatActivity() {
    lateinit var mEdit: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        mEdit = findViewById(R.id.edit_text);
    }

    fun appStart(view: View) {
        if (pass()) startActivity(Intent(this, MainActivity::class.java))
    }

    fun pinkStart(view: View) {
        if (pass()) startActivity(Intent(this, MainActivity::class.java).setAction("pink"))
    }

    fun queenStart(view: View) {
        if (pass()) startActivity(Intent(this, MainActivity::class.java).setAction("queen"))
    }

    fun bigStart(view: View) {
        if (pass()) startActivity(Intent(this, MainActivity::class.java).setAction("big"))
    }

    private fun pass(): Boolean {
        return "hhh" == mEdit.text.toString()
    }
}