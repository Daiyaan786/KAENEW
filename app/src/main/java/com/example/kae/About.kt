package com.example.kae

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.kae.R.id.btnAboutBackBtn
import com.example.kae.R.layout.about


class About : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about)

        val backButton = findViewById<View>(btnAboutBackBtn)
//BACK BUTTON TO MAINACTIVITY
        backButton.setOnClickListener {
            val intent = Intent(this@About, MainActivity::class.java)
            startActivity(intent) }
    }
}