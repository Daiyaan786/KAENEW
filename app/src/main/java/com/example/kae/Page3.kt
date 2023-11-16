package com.example.kae

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Page3 : AppCompatActivity() {

    // Define a reference to the database
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    // Unused, a root *node* reference
    private val reference: DatabaseReference = database.getReference()

    private lateinit var vanneNameEditText: EditText
    private lateinit var skoolGraadEditText: EditText
    private lateinit var VBBCBEditText: EditText
    private lateinit var NBCBEditText: EditText
    private lateinit var OnbCBEditText: EditText
    private lateinit var VBBFBEditText: EditText
    private lateinit var NBFBEditText: EditText
    private lateinit var OnbFBEditText: EditText
    private lateinit var SonBEditText: EditText
    private lateinit var ConcBEditText: EditText
    private lateinit var FaceToFaceCheckBox: CheckBox
    private lateinit var VideoCheckBox: CheckBox
    private var FaceToFace: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page3)
        //
        val entryDescription = "Blokfluit (Recorder) Solo"
        vanneNameEditText = findViewById(R.id.editTextText4)
        skoolGraadEditText = findViewById(R.id.editTextText5)
        VBBCBEditText = findViewById(R.id.editTextText9)
        NBCBEditText = findViewById(R.id.editTextText10)
        OnbCBEditText = findViewById(R.id.editTextText11)
        VBBFBEditText = findViewById(R.id.editTextText12)
        NBFBEditText = findViewById(R.id.editTextText13)
        OnbFBEditText = findViewById(R.id.editTextText14)
        SonBEditText = findViewById(R.id.editTextText15)
        ConcBEditText = findViewById(R.id.editTextText16)
        FaceToFaceCheckBox = findViewById(R.id.checkBox3)
        VideoCheckBox = findViewById(R.id.checkBox4)
        val button: Button = findViewById(R.id.button24)
        //
        //SUBMIT BUTTON
        button.setOnClickListener {
            //
            val vanneEnName = vanneNameEditText.text.toString()
            val skoolGraad = skoolGraadEditText.text.toString()
            val VBBCB = VBBCBEditText.text.toString()
            val NBCB = NBCBEditText.text.toString()
            val OnbCB = OnbCBEditText.text.toString()
            val VBBFB = VBBFBEditText.text.toString()
            val NBFB = NBFBEditText.text.toString()
            val OnbFB = OnbFBEditText.text.toString()
            val SonB = SonBEditText.text.toString()
            val ConcB = ConcBEditText.text.toString()
            //

            // Check if both checkboxes are checked
            if (FaceToFaceCheckBox.isChecked && VideoCheckBox.isChecked) {
                showToast("Please select only one option (Face-To-Face or Video)")
            }
            //FaceToFace is checked
            else if (FaceToFaceCheckBox.isChecked) {
                FaceToFace=true
                submitDataToFirebase(entryDescription,vanneEnName, skoolGraad, VBBCB, NBCB, OnbCB, VBBFB, NBFB, OnbFB, SonB, ConcB, FaceToFace)
            }
            //Video is checked
            else if (VideoCheckBox.isChecked) {
                FaceToFace=false
                submitDataToFirebase(entryDescription,vanneEnName, skoolGraad, VBBCB, NBCB, OnbCB, VBBFB, NBFB, OnbFB, SonB, ConcB, FaceToFace)
            }
            // If neither checkbox is checked
            else {
                showToast("Please select either Face-To-Face or Video")
            }
        }
    }
    //

    // Function To Submit Data To Firebase
    private fun submitDataToFirebase(description: String, vanneEnName: String, skoolGraad: String, VBBCB: String, NBCB: String, OnbCB: String, VBBFB: String, NBFB: String, OnbFB: String, SonB: String, ConcB: String, FaceToFace: Boolean) {
        data class DataClass(
            val vanneEnName: String,
            val skoolGraad: String,
            val VBBCB: String,
            val NBCB: String,
            val OnbCB: String,
            val VBBFB: String,
            val NBFB: String,
            val OnbFB: String,
            val SonB: String,
            val ConcB: String,
            val FaceToFace: Boolean
        )
        // Replace with your data
        val userData = DataClass(
            vanneEnName,
            skoolGraad,
            VBBCB,
            NBCB,
            OnbCB,
            VBBFB,
            NBFB,
            OnbFB,
            SonB,
            ConcB,
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
        Toast.makeText(this@Page3, message, Toast.LENGTH_SHORT).show()
    }
}