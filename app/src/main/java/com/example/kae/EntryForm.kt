package com.example.kae

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class EntryForm : AppCompatActivity() {

    private val pageNames = arrayOf("Blokfluit/Recorder Duet", "Blokfluit/Recorder : Ensembles", "Blokfluit/Recorder : Solo", "Ensembles en Orkeste",
        "Fotografie/Photography","Houtblaasinstrumente/Woodwinds : Duet" , "Houtblaasinstrumente: Sol Woodwinds : Solo" , "Klassieke Kitaar : Duet" ,
        "Klassieke Kitaar : Solo", "Junior Klavier Skoolgraad 6-7 Solo", "Junior Klavier : Solo Skoolgraad R - 5" ,  "Klavier Duet : Senior" ,
        "Senior Klavier : Solo" , "Samespel Klavier : Skoolgraad 3- Piano Duet : School Grade 3-5","Samespel Klavier : Skoolgraad 6-7 Piano Duet : School Grades 6-7",
        "KOORSANG/CHOIR 2023" , "Koperblaasinstrumente/Brass : Solo" , "Koperblaasinstrumente/Brass : Duet" , "INSKRYWINGSVORM: KUNS","Letterkunde" ,
        "Poetry Junior" ,  "Poetry Senior" , "Meerstemmige Sang : Junior" , "Sang/Vocal Junior : Solo" , "Meerstemmige Sang : Senior" , "Sang/Vocal Senior : Solo",
        "Spraakkuns Addisionele Taal Junior - Groepe" , "Spraak Addisionele Taal Junior" , "Spraak Addisionele Taal Senior" , "Spraakkuns en Mimiek Junior" ,
        "Spraakkuns en Mimiek Junior Groepe" , "Spraakkuns Senior" , "Strykinstrumente Junior : Solo" ,  "Strykinstrumente Junior : Duet" ,
        "Strykinstrumente Senior : Solo", "Strykinstrumente Senior : Duet" , "Junior Dromstel/Percussion" , "Senior Dromstel/Percussion"

    )

    // Define a reference to the database
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    // a root *node* reference
    private val reference: DatabaseReference = database.getReference()

    private lateinit var SchoolTextView: TextView
    private lateinit var SchoolPhoneNumTextView: TextView
    private lateinit var PhoneNumTextView: TextView
    private lateinit var EmailTextView: TextView
    private lateinit var SchoolAddressTextView: TextView
    private lateinit var ChildrenTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.entryform)
        //
        SchoolTextView = findViewById(R.id.editTextText193)
        SchoolPhoneNumTextView = findViewById(R.id.editTextText194)
        PhoneNumTextView = findViewById(R.id.editTextText195)
        EmailTextView = findViewById(R.id.editTextText196)
        SchoolAddressTextView = findViewById(R.id.editTextText197)
        ChildrenTextView = findViewById(R.id.textView77)
        //
        loadTeacherInfo()
        //
        val spinner = findViewById<Spinner>(R.id.spinner)

        // Create an ArrayAdapter and set it to the Spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, pageNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        // Set an OnItemSelectedListener for the Spinner
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = pageNames[position]
                navigateToPage(selectedItem)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle when nothing is selected (if needed)
            }
        }
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

    private fun navigateToPage(pageName: String) {
        when (pageName) {
            "Blokfluit/Recorder Duet" -> {
                // Start an activity or fragment for Page 1
                val intent = Intent(this, Page1::class.java)
                startActivity(intent)
            }
            "Blokfluit/Recorder : Ensembles" -> {
                // Start an activity or fragment for Page 2
                val intent = Intent(this, Page2::class.java)
                startActivity(intent)
            }
            // Add more cases for other pages as needed
            "Blokfluit/Recorder : Solo" -> {
                // Start an activity or fragment for Page 2
                val intent = Intent(this, Page3::class.java)
                startActivity(intent)
            }
            "Ensembles en Orkeste" -> {
                // Start an activity or fragment for Page 2
                val intent = Intent(this, Page4::class.java)
                startActivity(intent)
            }
            "Fotografie/Photography" -> {
                // Start an activity or fragment for Page 2
                val intent = Intent(this, Page5::class.java)
                startActivity(intent)
            }
            "Houtblaasinstrumente/Woodwinds : Duet" -> {
                // Start an activity or fragment for Page 2
                val intent = Intent(this, Page6::class.java)
                startActivity(intent)
            }
            "Houtblaasinstrumente: Sol Woodwinds : Solo" -> {
                // Start an activity or fragment for Page 2
                val intent = Intent(this, Page7::class.java)
                startActivity(intent)
            }
            "Klassieke Kitaar : Duet" -> {
                // Start an activity or fragment for Page 2
                val intent = Intent(this, Page8::class.java)
                startActivity(intent)
            }
            "Klassieke Kitaar : Solo" -> {
                // Start an activity or fragment for Page 2
                val intent = Intent(this, Page9::class.java)
                startActivity(intent)
            }
            "Junior Klavier Skoolgraad 6-7 Solo" -> {
                // Start an activity or fragment for Page 2
                val intent = Intent(this, Page10::class.java)
                startActivity(intent)
            }
            "Junior Klavier : Solo Skoolgraad R - 5" -> {
                // Start an activity or fragment for Page 1
                val intent = Intent(this, Page11::class.java)
                startActivity(intent)
            }
            "Klavier Duet : Senior" -> {
                // Start an activity or fragment for Page 2
                val intent = Intent(this, Page12::class.java)
                startActivity(intent)
            }
            // Add more cases for other pages as needed
            "Senior Klavier : Solo" -> {
                // Start an activity or fragment for Page 2
                val intent = Intent(this, Page13::class.java)
                startActivity(intent)
            }
            "Samespel Klavier : Skoolgraad 3- Piano Duet : School Grade 3-5" -> {
                // Start an activity or fragment for Page 2
                val intent = Intent(this, Page14::class.java)
                startActivity(intent)
            }
            "Samespel Klavier : Skoolgraad 6-7 Piano Duet : School Grades 6-7" -> {
                // Start an activity or fragment for Page 2
                val intent = Intent(this, Page15::class.java)
                startActivity(intent)
            }
            "KOORSANG/CHOIR 2023" -> {
                // Start an activity or fragment for Page 2
                val intent = Intent(this, Page16::class.java)
                startActivity(intent)
            }
            "Koperblaasinstrumente/Brass : Solo" -> {
                // Start an activity or fragment for Page 2
                val intent = Intent(this, Page17::class.java)
                startActivity(intent)
            }
            "Koperblaasinstrumente/Brass : Duet" -> {
                // Start an activity or fragment for Page 2
                val intent = Intent(this, Page18::class.java)
                startActivity(intent)
            }
            "INSKRYWINGSVORM: KUNS" -> {
                // Start an activity or fragment for Page 2
                val intent = Intent(this, Page19::class.java)
                startActivity(intent)
            }
            "Letterkunde" -> {
                // Start an activity or fragment for Page 2
                val intent = Intent(this, Page20::class.java)
                startActivity(intent)
            }
            "Poetry Junior" -> {
                // Start an activity or fragment for Page 1
                val intent = Intent(this, Page21::class.java)
                startActivity(intent)
            }
            "Poetry Senior" -> {
                // Start an activity or fragment for Page 2
                val intent = Intent(this, Page22::class.java)
                startActivity(intent)
            }
            // Add more cases for other pages as needed
            "Meerstemmige Sang : Junior" -> {
                // Start an activity or fragment for Page 2
                val intent = Intent(this, Page23::class.java)
                startActivity(intent)
            }
            "Sang/Vocal Junior : Solo" -> {
                // Start an activity or fragment for Page 2
                val intent = Intent(this, Page24::class.java)
                startActivity(intent)
            }
            "Meerstemmige Sang : Senior" -> {
                // Start an activity or fragment for Page 2
                val intent = Intent(this, Page25::class.java)
                startActivity(intent)
            }
            "Sang/Vocal Senior : Solo" -> {
                // Start an activity or fragment for Page 2
                val intent = Intent(this, Page26::class.java)
                startActivity(intent)
            }
            "Spraakkuns Addisionele Taal Junior - Groepe" -> {
                // Start an activity or fragment for Page 2
                val intent = Intent(this, Page27::class.java)
                startActivity(intent)
            }
            "Spraak Addisionele Taal Junior" -> {
                // Start an activity or fragment for Page 2
                val intent = Intent(this, Page28::class.java)
                startActivity(intent)
            }
            "Spraak Addisionele Taal Senior" -> {
                // Start an activity or fragment for Page 2
                val intent = Intent(this, Page29::class.java)
                startActivity(intent)
            }
            "Spraakkuns en Mimiek Junior" -> {
                // Start an activity or fragment for Page 2
                val intent = Intent(this, Page30::class.java)
                startActivity(intent)
            }
            "Spraakkuns en Mimiek Junior Groepe" -> {
                // Start an activity or fragment for Page 1
                val intent = Intent(this, Page31::class.java)
                startActivity(intent)
            }
            "Spraakkuns Senior" -> {
                // Start an activity or fragment for Page 2
                val intent = Intent(this, Page32::class.java)
                startActivity(intent)
            }
            // Add more cases for other pages as needed
            "Strykinstrumente Junior : Solo" -> {
                // Start an activity or fragment for Page 2
                val intent = Intent(this, Page33::class.java)
                startActivity(intent)
            }
            "Strykinstrumente Junior : Duet" -> {
                // Start an activity or fragment for Page 2
                val intent = Intent(this, Page34::class.java)
                startActivity(intent)
            }
            "Strykinstrumente Senior : Solo" -> {
                // Start an activity or fragment for Page 2
                val intent = Intent(this, Page35::class.java)
                startActivity(intent)
            }
            "Strykinstrumente Senior : Duet" -> {
                // Start an activity or fragment for Page 2
                val intent = Intent(this, Page36::class.java)
                startActivity(intent)
            }
            "Junior Dromstel/Percussion" -> {
                // Start an activity or fragment for Page 2
                val intent = Intent(this, Page37::class.java)
                startActivity(intent)
            }
            "Senior Dromstel/Percussion" -> {
                // Start an activity or fragment for Page 2
                val intent = Intent(this, Page38::class.java)
                startActivity(intent)
            }
        }
    }

    // FUNCTION TO LOAD THE TEACHER'S INFO
    private fun loadTeacherInfo() {
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
                        val school = dataSnapshot.child("school").getValue(String::class.java)
                        val schoolPhoneNum =
                            dataSnapshot.child("schoolPhoneNum").getValue(String::class.java)
                        val phoneNum = dataSnapshot.child("phoneNum").getValue(String::class.java)
                        val email = dataSnapshot.child("email").getValue(String::class.java)
                        val schoolAddress =
                            dataSnapshot.child("schoolAddress").getValue(String::class.java)
                        val numChildren = dataSnapshot.child("numChildren").getValue(Int::class.java)

                        // Initialize StringBuilder for ChildrenTextView
                        val childrenTextBuilder = StringBuilder()

                        // Loop through children and append their names to the StringBuilder
                        if (numChildren != null) {
                            for (i in 1..numChildren) {
                                val childName = dataSnapshot.child("child $i").getValue(String::class.java)
                                if (!childName.isNullOrBlank()) {
                                    childrenTextBuilder.append("$childName\n")
                                }
                            }
                        }

                        // Update the TextViews with the retrieved information
                        SchoolTextView.text = school
                        SchoolPhoneNumTextView.text = schoolPhoneNum
                        PhoneNumTextView.text = phoneNum
                        EmailTextView.text = email
                        SchoolAddressTextView.text = schoolAddress
                        ChildrenTextView.text = childrenTextBuilder.toString()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors here
                }
            })
        }
    }
}





