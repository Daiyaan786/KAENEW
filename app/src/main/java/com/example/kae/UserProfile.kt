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

class UserProfile : AppCompatActivity() {


    // Define a reference to the database
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    // a root *node* reference
    private val reference: DatabaseReference = database.getReference()

    private lateinit var NameTextView: TextView
    private lateinit var EmailTextView: TextView
    private lateinit var PhoneNumTextView: TextView
    private lateinit var SchoolTextView: TextView
    private lateinit var SchoolAddressTextView: TextView
    private lateinit var SchoolPhoneNumTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.userprofile)
        //
        NameTextView = findViewById(R.id.textView81)
        EmailTextView = findViewById(R.id.textView82)
        PhoneNumTextView = findViewById(R.id.textView83)
        SchoolTextView = findViewById(R.id.textView84)
        SchoolAddressTextView = findViewById(R.id.textView85)
        SchoolPhoneNumTextView = findViewById(R.id.textView86)

        //BACK BUTTON
        val backButton = findViewById<View>(R.id.button44)
        backButton.setOnClickListener {
            val intent = Intent(this@UserProfile, MainActivity::class.java)
            startActivity(intent) }
        //

        // LOADING TEACHER'S USER PROFILE
        // Get teacher's persisting ID from TeacherIdHolder
        val teacherId = TeacherIdHolder.teacherId

        // Check if the teacherId is not null before querying the database
        if (!teacherId.isNullOrBlank()) {
            // Reference to the specific teacher's node in the database
            val teacherNodeRef = reference.child("Teachers").child(teacherId)

            // Read the data from the database
            teacherNodeRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // Check if the dataSnapshot exists
                    if (dataSnapshot.exists()) {
                        // Retrieve the teacher's information
                        val name = dataSnapshot.child("name").getValue(String::class.java)
                        val email = dataSnapshot.child("email").getValue(String::class.java)
                        val phoneNum = dataSnapshot.child("phoneNum").getValue(String::class.java)
                        val school = dataSnapshot.child("school").getValue(String::class.java)
                        val schoolAddress = dataSnapshot.child("schoolAddress").getValue(String::class.java)
                        val schoolPhoneNum = dataSnapshot.child("schoolPhoneNum").getValue(String::class.java)

                        // Update the TextViews with the retrieved information
                        NameTextView.text = name
                        EmailTextView.text = email
                        PhoneNumTextView.text = phoneNum
                        SchoolTextView.text = school
                        SchoolAddressTextView.text = schoolAddress
                        SchoolPhoneNumTextView.text = schoolPhoneNum
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
    }
}