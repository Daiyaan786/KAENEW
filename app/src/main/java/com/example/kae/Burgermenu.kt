package com.example.kae

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.kae.R.id.app_bar_search
import com.example.kae.R.id.entries
import com.example.kae.R.id.user_profile
import com.example.kae.R.id.video_entries
import com.example.kae.R.layout.burgermenu


class Burgermenu : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(burgermenu)


    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            app_bar_search -> {
                // Handle the Search action
                return true
            }

            user_profile -> {
                // Start the User Profile activity
                val userProfileIntent = Intent(this, UserProfile::class.java)
                startActivity(userProfileIntent)
                return true
            }

            entries -> {
                // Start the Entries activity
                val entriesIntent = Intent(this, EntryForm::class.java)
                startActivity(entriesIntent)
                return true
            }

            video_entries -> {
                // Start the Video Entries activity
                val videoEntriesIntent = Intent(this, VideoEntries::class.java)
                startActivity(videoEntriesIntent)
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }
}


