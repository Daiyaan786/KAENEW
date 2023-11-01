package com.example.kae

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Button
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Page1: AppCompatActivity() {
    // Define a reference to the database
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance("https://kaemobile-68b15-default-rtdb.europe-west1.firebasedatabase.app/")
    private val reference: DatabaseReference = database.getReference("your_data_path")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page1)

        //
        val button: Button = findViewById(R.id.button)

        // Set an OnClickListener for the button
        button.setOnClickListener {
            submitDataToFirebase()
        }
    }

    private fun submitDataToFirebase() {
        data class YourDataClass(
            val KLAS: String,
            val DESCRIPTION: String,
            val NR: Int,
            val NAAM: String,
            val SKOOL: String,
            val GRAAD: String,
            val SAMEROEPER: String,
            val V_S: String,
            val Bedrag: Double,
            val E_pos: String,
            val Afrigter: String
        )
        // Replace with your data
        val yourData = YourDataClass(
            "Class123",
            "This is a description",
            1,
            "John Doe",
            "ABC School",
            "Grade 9",
            "Some occupation",
            "V/S Value",
            99.99,
            "example@email.com",
            "Coach Name"
        )

        // Push the data to the database
        reference.push().setValue(yourData)
    }
}