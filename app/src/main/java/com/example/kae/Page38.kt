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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Page38 : AppCompatActivity() {
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
    private lateinit var DromstelJPEditText: EditText
    private lateinit var PerkussieJPEditText: EditText
    private lateinit var DuetOrTrioJPEditText: EditText
    private lateinit var FaceToFaceCheckBox: CheckBox
    private lateinit var VideoCheckBox: CheckBox
    private var FaceToFace: Boolean = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page38)
        //
        val entryDescription = "Dromstel(Percussion) Senior"
        vanneNameEditText = findViewById(R.id.editTextText190)
        SkoolGraadEditText = findViewById(R.id.editTextText192)
        DromstelJPEditText = findViewById(R.id.editTextText188)
        PerkussieJPEditText = findViewById(R.id.editTextText189)
        DuetOrTrioJPEditText = findViewById(R.id.editTextText191)
        FaceToFaceCheckBox = findViewById(R.id.checkBox68)
        VideoCheckBox = findViewById(R.id.checkBox67)
        val button: Button = findViewById(R.id.button33)
        //
        //SUBMIT BUTTON
        button.setOnClickListener {
            //
            val vanneEnName = vanneNameEditText.text.toString()
            val SkoolGraad = SkoolGraadEditText.text.toString()
            val DromstelJP = DromstelJPEditText.text.toString()
            val PerkussieJP = PerkussieJPEditText.text.toString()
            val DuetOrTrioJP = DuetOrTrioJPEditText.text.toString()
            //

            // Check if both checkboxes are checked
            if (FaceToFaceCheckBox.isChecked && VideoCheckBox.isChecked) {
                showToast("Please select only one option (Face-To-Face or Video)")
            }
            //FaceToFace is checked
            else if (FaceToFaceCheckBox.isChecked) {
                FaceToFace=true
                submitDataToFirebase(entryDescription,vanneEnName, SkoolGraad, DromstelJP, PerkussieJP, DuetOrTrioJP, FaceToFace)
            }
            //Video is checked
            else if (VideoCheckBox.isChecked) {
                FaceToFace=false
                submitDataToFirebase(entryDescription,vanneEnName, SkoolGraad, DromstelJP, PerkussieJP, DuetOrTrioJP, FaceToFace)
            }
            // If neither checkbox is checked
            else {
                showToast("Please select either Face-To-Face or Video")
            }
        }
    }

    // Function To Submit Data To Firebase
    private fun submitDataToFirebase(description: String, vanneEnName: String, SkoolGraad: String, DromstelJP: String, PerkussieJP: String, DuetOrTrioJP: String, FaceToFace: Boolean) {
        data class DataClass(
            val vanneEnName: String,
            val SkoolGraad: String,
            val DromstelJP: String,
            val PerkussieJP: String,
            val DuetOrTrioJP: String,
            val FaceToFace: Boolean
        )
        // Replace with your data
        val userData = DataClass(
            vanneEnName,
            SkoolGraad,
            DromstelJP,
            PerkussieJP,
            DuetOrTrioJP,
            FaceToFace
        )

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
        Toast.makeText(this@Page38, message, Toast.LENGTH_SHORT).show()
    }
}