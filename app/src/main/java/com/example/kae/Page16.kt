package com.example.kae

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Page16 : AppCompatActivity() {

    // Define a reference to the database
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    // Unused, a root *node* reference
    private val reference: DatabaseReference = database.getReference()

    private lateinit var vanneNameEditText: EditText
    private lateinit var NaamVanSkoolEditText: EditText
    private lateinit var GetalKoorledeEditText: EditText
    private lateinit var SKEditText: EditText
    private lateinit var FaceToFaceCheckBox: CheckBox
    private lateinit var VideoCheckBox: CheckBox
    private var FaceToFace: Boolean = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page16)
        //
        val entryDescription = "Koorsang (Choir)"
        vanneNameEditText = findViewById(R.id.editTextText73)
        NaamVanSkoolEditText = findViewById(R.id.editTextText74)
        GetalKoorledeEditText = findViewById(R.id.editTextText75)
        SKEditText = findViewById(R.id.editTextText76)
        FaceToFaceCheckBox = findViewById(R.id.checkBox31)
        VideoCheckBox = findViewById(R.id.checkBox32)
        val button: Button = findViewById(R.id.button9)
        //
        //SUBMIT BUTTON
        button.setOnClickListener {
            //
            val vanneEnName = vanneNameEditText.text.toString()
            val NaamVanSkool = NaamVanSkoolEditText.text.toString()
            val GetalKoorlede = GetalKoorledeEditText.text.toString()
            val SK = SKEditText.text.toString()
            //

            // Check if both checkboxes are checked
            if (FaceToFaceCheckBox.isChecked && VideoCheckBox.isChecked) {
                showToast("Please select only one option (Face-To-Face or Video)")
            }
            //FaceToFace is checked
            else if (FaceToFaceCheckBox.isChecked) {
                FaceToFace=true
                submitDataToFirebase(entryDescription,vanneEnName, NaamVanSkool, GetalKoorlede, SK, FaceToFace)
            }
            //Video is checked
            else if (VideoCheckBox.isChecked) {
                FaceToFace=false
                submitDataToFirebase(entryDescription,vanneEnName, NaamVanSkool, GetalKoorlede, SK, FaceToFace)
            }
            // If neither checkbox is checked
            else {
                showToast("Please select either Face-To-Face or Video")
            }
        }
    }

    // Function To Submit Data To Firebase
    private fun submitDataToFirebase(description: String, vanneEnName: String, NaamVanSkool: String, GetalKoorlede: String, SK: String, FaceToFace: Boolean) {
        data class DataClass(
            val vanneEnName: String,
            val NaamVanSkool: String,
            val GetalKoorlede: String,
            val SK: String,
            val FaceToFace: Boolean
        )
        // Replace with your data
        val userData = DataClass(
            vanneEnName,
            NaamVanSkool,
            GetalKoorlede,
            SK,
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
        Toast.makeText(this@Page16, message, Toast.LENGTH_SHORT).show()
    }
}