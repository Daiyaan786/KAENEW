package com.example.kae

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Page32 : AppCompatActivity() {

    // Define a reference to the database
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    // Unused, a root *node* reference
    private val reference: DatabaseReference = database.getReference()

    private lateinit var vanneNameEditText: EditText
    private lateinit var GraadEditText: EditText
    private lateinit var GedigEGEditText: EditText
    private lateinit var ProsaEPEditText: EditText
    private lateinit var MondKommMKEditText: EditText
    private lateinit var LeesOLEditText: EditText
    private lateinit var MonoloogMOEditText: EditText
    private lateinit var ProsaGPEditText: EditText
    private lateinit var LetterGEPEEditText: EditText
    private lateinit var FaceToFaceCheckBox: CheckBox
    private lateinit var VideoCheckBox: CheckBox
    private var FaceToFace: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page32)
        //
        val entryDescription = "Spraak Senior"
        vanneNameEditText = findViewById(R.id.editTextText155)
        GraadEditText = findViewById(R.id.editTextText161)
        GedigEGEditText = findViewById(R.id.editTextText163)
        ProsaEPEditText = findViewById(R.id.editTextText159)
        MondKommMKEditText = findViewById(R.id.editTextText156)
        LeesOLEditText = findViewById(R.id.editTextText166)
        MonoloogMOEditText = findViewById(R.id.editTextText157)
        ProsaGPEditText = findViewById(R.id.editTextText165)
        LetterGEPEEditText = findViewById(R.id.editTextText160)
        FaceToFaceCheckBox = findViewById(R.id.checkBox55)
        VideoCheckBox = findViewById(R.id.checkBox56)
        val button: Button = findViewById(R.id.button27)
        //
        //SUBMIT BUTTON
        button.setOnClickListener {
            //
            val vanneEnName = vanneNameEditText.text.toString()
            val Graad = GraadEditText.text.toString()
            val GedigEG = GedigEGEditText.text.toString()
            val ProsaEP = ProsaEPEditText.text.toString()
            val MondKommMK = MondKommMKEditText.text.toString()
            val LeesOL = LeesOLEditText.text.toString()
            val MonoloogMO = MonoloogMOEditText.text.toString()
            val ProsaGP = ProsaGPEditText.text.toString()
            val LetterGEPE = LetterGEPEEditText.text.toString()
            //

            // Check if both checkboxes are checked
            if (FaceToFaceCheckBox.isChecked && VideoCheckBox.isChecked) {
                showToast("Please select only one option (Face-To-Face or Video)")
            }
            //FaceToFace is checked
            else if (FaceToFaceCheckBox.isChecked) {
                FaceToFace=true
                submitDataToFirebase(entryDescription,vanneEnName, Graad, GedigEG, ProsaEP, MondKommMK, LeesOL, MonoloogMO, ProsaGP, LetterGEPE, FaceToFace)
            }
            //Video is checked
            else if (VideoCheckBox.isChecked) {
                FaceToFace=false
                submitDataToFirebase(entryDescription,vanneEnName, Graad, GedigEG, ProsaEP, MondKommMK, LeesOL, MonoloogMO, ProsaGP, LetterGEPE, FaceToFace)
            }
            // If neither checkbox is checked
            else {
                showToast("Please select either Face-To-Face or Video")
            }
        }
    }
    //

    // Function To Submit Data To Firebase
    private fun submitDataToFirebase(description: String, vanneEnName: String, Graad: String, GedigEG: String, ProsaEP: String, MondKommMK: String, LeesOL: String, MonoloogMO: String, ProsaGP: String, LetterGEPE: String, FaceToFace: Boolean) {
        data class DataClass(
            val vanneEnName: String,
            val Graad: String,
            val GedigEG: String,
            val ProsaEP: String,
            val MondKommMK: String,
            val LeesOL: String,
            val MonoloogMO: String,
            val ProsaGP: String,
            val LetterGEPE: String,
            val FaceToFace: Boolean
        )
        // Replace with your data
        val userData = DataClass(
            vanneEnName,
            Graad,
            GedigEG,
            ProsaEP,
            MondKommMK,
            LeesOL,
            MonoloogMO,
            ProsaGP,
            LetterGEPE,
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
        Toast.makeText(this@Page32, message, Toast.LENGTH_SHORT).show()
    }
}