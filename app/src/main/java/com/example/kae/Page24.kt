package com.example.kae

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.internal.artificialFrame

class Page24 : AppCompatActivity() {

    // Define a reference to the database
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    // Unused, a root *node* reference
    private val reference: DatabaseReference = database.getReference()

    private lateinit var vanneNameEditText: EditText
    private lateinit var MSEditText: EditText
    private lateinit var SkoolGraadEditText: EditText
    private lateinit var AfrLiriekeSJEditText: EditText
    private lateinit var EieKeuseSJEditText: EditText
    private lateinit var VolksLiedjieSJEditText: EditText
    private lateinit var BlyspelSJEditText: EditText
    private lateinit var FaceToFaceCheckBox: CheckBox
    private lateinit var VideoCheckBox: CheckBox
    private var FaceToFace: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page24)
        //
        val entryDescription = "Sang Junior Solo"
        vanneNameEditText = findViewById(R.id.editTextText110)
        MSEditText = findViewById(R.id.editTextText108)
        SkoolGraadEditText = findViewById(R.id.editTextText109)
        AfrLiriekeSJEditText = findViewById(R.id.editTextText104)
        EieKeuseSJEditText = findViewById(R.id.editTextText107)
        VolksLiedjieSJEditText = findViewById(R.id.editTextText105)
        BlyspelSJEditText = findViewById(R.id.editTextText106)
        FaceToFaceCheckBox = findViewById(R.id.checkBox39)
        VideoCheckBox = findViewById(R.id.checkBox40)
        val button: Button = findViewById(R.id.button18)
        //
        //SUBMIT BUTTON
        button.setOnClickListener {
            //
            val vanneEnName = vanneNameEditText.text.toString()
            val MS = MSEditText.text.toString()
            val SkoolGraad = SkoolGraadEditText.text.toString()
            val AfrLiriekeSJ = AfrLiriekeSJEditText.text.toString()
            val EieKeuseSJ = EieKeuseSJEditText.text.toString()
            val VolksLiedjieSJ = VolksLiedjieSJEditText.text.toString()
            val BlyspelSJ = BlyspelSJEditText.text.toString()
            //

            // Check if both checkboxes are checked
            if (FaceToFaceCheckBox.isChecked && VideoCheckBox.isChecked) {
                showToast("Please select only one option (Face-To-Face or Video)")
            }
            //FaceToFace is checked
            else if (FaceToFaceCheckBox.isChecked) {
                FaceToFace=true
                submitDataToFirebase(entryDescription,vanneEnName, MS, SkoolGraad, AfrLiriekeSJ, EieKeuseSJ, VolksLiedjieSJ, BlyspelSJ, FaceToFace)
            }
            //Video is checked
            else if (VideoCheckBox.isChecked) {
                FaceToFace=false
                submitDataToFirebase(entryDescription,vanneEnName, MS, SkoolGraad, AfrLiriekeSJ, EieKeuseSJ, VolksLiedjieSJ, BlyspelSJ, FaceToFace)
            }
            // If neither checkbox is checked
            else {
                showToast("Please select either Face-To-Face or Video")
            }
        }
    }

    // Function To Submit Data To Firebase
    private fun submitDataToFirebase(description: String, vanneEnName: String, MS: String, SkoolGraad: String, AfrLiriekeSJ: String, EieKeuseSJ: String, VolksLiedjieSJ: String, BlyspelSJ: String, FaceToFace: Boolean) {
        data class DataClass(
            val vanneEnName: String,
            val MS: String,
            val SkoolGraad: String,
            val AfrLiriekeSJ: String,
            val EieKeuseSJ: String,
            val VolksLiedjieSJ: String,
            val BlyspelSJ: String,
            val FaceToFace: Boolean
        )
        // Replace with your data
        val userData = DataClass(
            vanneEnName,
            MS,
            SkoolGraad,
            AfrLiriekeSJ,
            EieKeuseSJ,
            VolksLiedjieSJ,
            BlyspelSJ,
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
        Toast.makeText(this@Page24, message, Toast.LENGTH_SHORT).show()
    }
}