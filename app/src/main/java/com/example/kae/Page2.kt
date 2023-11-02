package com.example.kae

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Page2 : AppCompatActivity() {
    // Define a reference to the database
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val reference: DatabaseReference = database.getReference()
    private lateinit var vanneNameEditText: EditText
    private lateinit var blokfluitEditText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page2)

        //
        vanneNameEditText = findViewById(R.id.editTextText7)
        blokfluitEditText = findViewById(R.id.editTextText8)
        //
        val button: Button = findViewById(R.id.button13)

//SUBMIT BUTTON
        button.setOnClickListener {
            val entryDescription = "Blokfluit (Recorder) Ensembles"
            val vanneEnName = vanneNameEditText.text.toString()
            val blokfluit = blokfluitEditText.text.toString()
            submitDataToFirebase(entryDescription,vanneEnName, blokfluit)
        }
    }

    private fun submitDataToFirebase(description: String, vanneEnName: String, blokfluit: String) {
        data class YourDataClass(
            val Description: String,
            val vanneEnName: String,
            val blokfluit: String
        )
        // Replace with your data
        val yourData = YourDataClass(
            description,
            vanneEnName,
            blokfluit
        )

        // Push the data to the database
        reference.push().setValue(yourData)
    }
}