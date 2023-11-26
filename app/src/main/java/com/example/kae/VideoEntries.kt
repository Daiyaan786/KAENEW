package com.example.kae

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class VideoEntries : AppCompatActivity() {

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

    // Define a reference to the database
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    // a root *node* reference
    private val reference: DatabaseReference = database.getReference()

    private lateinit var VideoEntryStateTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.videoentires)
        val backButton = findViewById<View>(R.id.button3)
        backButton.setOnClickListener {
            val intent = Intent(this@VideoEntries, StartPage::class.java)
            startActivity(intent) }

        val button = findViewById<Button>(R.id.button46) // Replace with your actual button ID

        // Set OnClickListener on the button
        button.setOnClickListener {
            // Define the URL you want to open
            val url =
                "https://docs.google.com/forms/d/e/1FAIpQLSfjrk_FwPvNNLF0zf6p5on9fx0s3OBn3_U4QNRujz-7n953uw/viewform"

            // Create an intent to open a web browser
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

            // Start the activity
            startActivity(intent)
        }
        //
        VideoEntryStateTextView = findViewById(R.id.textView89)

        // ValueEventListener to get 'VideoEntryState' from the Database
        reference.child("VideoEntryState").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get the value from the dataSnapshot
                val videoEntryState = dataSnapshot.getValue(String::class.java)

                // Update TextView with retrieved value
                VideoEntryStateTextView.text = videoEntryState
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors here
            }
        })
    }
}