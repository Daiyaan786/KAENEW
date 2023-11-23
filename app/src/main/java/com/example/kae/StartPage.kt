package com.example.kae

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class StartPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.startpage)



        val video = findViewById<View>(R.id.button45)
        video.setOnClickListener {
            val intent = Intent(this@StartPage, VideoEntries::class.java)
            startActivity(intent) }


        val userPro = findViewById<View>(R.id.button41)
        userPro.setOnClickListener {
            val intent = Intent(this@StartPage, UserProfile::class.java)
            startActivity(intent) }


        val entry = findViewById<View>(R.id.button40)
        entry.setOnClickListener {
            val intent = Intent(this@StartPage, EntryForm::class.java)
            startActivity(intent) }

        // ... (other code if needed)

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.burgermenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.user_profile -> startActivity(Intent(this, UserProfile::class.java))
            R.id.entries -> startActivity(Intent(this, EntryForm::class.java))
            R.id.video_entries -> startActivity(Intent(this, VideoEntries::class.java))
            // Add more cases for other menu items if needed

            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }
}