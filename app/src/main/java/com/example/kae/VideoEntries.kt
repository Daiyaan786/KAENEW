package com.example.kae

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class VideoEntries : AppCompatActivity() {

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
            val intent = Intent(this@VideoEntries, EntryForm::class.java)
            startActivity(intent) }
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