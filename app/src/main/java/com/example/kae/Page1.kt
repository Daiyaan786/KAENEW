package com.example.kae

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Page1: AppCompatActivity() {

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
    // Unused, a root *node* reference
    private val reference: DatabaseReference = database.getReference()

    private lateinit var vanneNameEditText: EditText
    private lateinit var SkoolGraadEditText: EditText
    private lateinit var BlokfluitEditText: EditText
    private lateinit var FaceToFaceCheckBox: CheckBox
    private lateinit var VideoCheckBox: CheckBox
    private var FaceToFace: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page1)
        //
        val entryDescription = "Blokfluit (Recorder) Duet"
        vanneNameEditText = findViewById(R.id.editTextText)
        SkoolGraadEditText = findViewById(R.id.editTextText2)
        BlokfluitEditText = findViewById(R.id.editTextText3)
        FaceToFaceCheckBox = findViewById(R.id.checkBox)
        VideoCheckBox = findViewById(R.id.checkBox2)
        val button: Button = findViewById(R.id.button)
        //
        //SUBMIT BUTTON
        button.setOnClickListener {
            //
            val vanneEnName = vanneNameEditText.text.toString()
            val SkoolGraad = SkoolGraadEditText.text.toString()
            val Blokfluit = BlokfluitEditText.text.toString()
            //

            // Check if both checkboxes are checked
            if (FaceToFaceCheckBox.isChecked && VideoCheckBox.isChecked) {
                showToast("Please select only one option (Face-To-Face or Video)")
            }
            //FaceToFace is checked
            else if (FaceToFaceCheckBox.isChecked) {
                FaceToFace=true
                submitDataToFirebase(entryDescription,vanneEnName, SkoolGraad, Blokfluit, FaceToFace)
                val intent = Intent(this@Page1, EntryForm::class.java)
                startActivity(intent)
            }
            //Video is checked
            else if (VideoCheckBox.isChecked) {
                FaceToFace=false
                submitDataToFirebase(entryDescription,vanneEnName, SkoolGraad, Blokfluit, FaceToFace)
                val intent1 = Intent(this@Page1, EntryForm::class.java)
                startActivity(intent1)
            }
            // If neither checkbox is checked
            else {
                showToast("Please select either Face-To-Face or Video")
            }
        }
    }

    // Function To Submit Data To Firebase
    private fun submitDataToFirebase(description: String, vanneEnName: String, SkoolGraad: String, Blokfluit: String, FaceToFace: Boolean) {
        data class DataClass(
            val vanneEnName: String,
            val SkoolGraad: String,
            val Blokfluit: String,
            val FaceToFace: Boolean
        )
        // Replace with your data
        val userData = DataClass(
            vanneEnName,
            SkoolGraad,
            Blokfluit,
            FaceToFace
        )
        FirebaseUtils.writeChildToTeacher(vanneEnName)

        // Reference to the node
        val Node = reference.child(description)

        // Read current number of children to determine the next key
        Node.get().addOnSuccessListener { snapshot ->
            val nextKey = (snapshot.childrenCount + 1).toString()

            // Specify path using child() method with the next key
            val nextPath = Node.child(nextKey)

            // Push data to specified path
            nextPath.setValue(userData)
                .addOnSuccessListener{
                    // Handle success
                    showToast("Great, the info was successfully submitted.")
                }
                .addOnFailureListener{
                    // Handle failure
                    showToast("Unfortunately, there was an error.")
                }
        }.addOnFailureListener { error ->
            // Handle failure [ if needed ]
        }
    }

    // Function to show a Toast message
    private fun showToast(message: String) {
        Toast.makeText(this@Page1, message, Toast.LENGTH_SHORT).show()
    }
}