package com.example.kae

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Page35 : AppCompatActivity() {

    // Define a reference to the database
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    // Unused, a root *node* reference
    private val reference: DatabaseReference = database.getReference()

    private lateinit var vanneNameEditText: EditText
    private lateinit var SkoolGraadEditText: EditText
    private lateinit var InstrumentEditText: EditText
    private lateinit var BKSTSEditText: EditText
    private lateinit var RMSTSEditText: EditText
    private lateinit var GroepSolosSTSEditText: EditText
    private lateinit var ConcertoSTSEditText: EditText
    private lateinit var FaceToFaceCheckBox: CheckBox
    private lateinit var VideoCheckBox: CheckBox
    private var FaceToFace: Boolean = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page35)
        //
        val entryDescription = "Strykinstrumente (Strings) Senior  Solo"
        vanneNameEditText = findViewById(R.id.editTextText178)
        SkoolGraadEditText = findViewById(R.id.editTextText177)
        InstrumentEditText = findViewById(R.id.editTextText174)
        BKSTSEditText = findViewById(R.id.editTextText175)
        RMSTSEditText = findViewById(R.id.editTextText180)
        GroepSolosSTSEditText = findViewById(R.id.editTextText164)
        ConcertoSTSEditText = findViewById(R.id.editTextText179)
        FaceToFaceCheckBox = findViewById(R.id.checkBox61)
        VideoCheckBox = findViewById(R.id.checkBox62)
        val button: Button = findViewById(R.id.button30)
        //
        //SUBMIT BUTTON
        button.setOnClickListener {
            //
            val vanneEnName = vanneNameEditText.text.toString()
            val SkoolGraad = SkoolGraadEditText.text.toString()
            val Instrument = InstrumentEditText.text.toString()
            val BKSTS = BKSTSEditText.text.toString()
            val RMSTS = RMSTSEditText.text.toString()
            val GroepSolosSTS = GroepSolosSTSEditText.text.toString()
            val ConcertoSTS = ConcertoSTSEditText.text.toString()
            //

            // Check if both checkboxes are checked
            if (FaceToFaceCheckBox.isChecked && VideoCheckBox.isChecked) {
                showToast("Please select only one option (Face-To-Face or Video)")
            }
            //FaceToFace is checked
            else if (FaceToFaceCheckBox.isChecked) {
                FaceToFace=true
                submitDataToFirebase(entryDescription,vanneEnName, SkoolGraad, Instrument, BKSTS, RMSTS, GroepSolosSTS, ConcertoSTS, FaceToFace)
            }
            //Video is checked
            else if (VideoCheckBox.isChecked) {
                FaceToFace=false
                submitDataToFirebase(entryDescription,vanneEnName, SkoolGraad, Instrument, BKSTS, RMSTS, GroepSolosSTS, ConcertoSTS, FaceToFace)
            }
            // If neither checkbox is checked
            else {
                showToast("Please select either Face-To-Face or Video")
            }
        }
    }

    // Function To Submit Data To Firebase
    private fun submitDataToFirebase(description: String, vanneEnName: String, SkoolGraad: String, Instrument: String, BKSTS: String, RMSTS: String, GroepSolosSTS: String, ConcertoSTS: String, FaceToFace: Boolean) {
        data class DataClass(
            val vanneEnName: String,
            val SkoolGraad: String,
            val Instrument: String,
            val BKSTS: String,
            val RMSTS: String,
            val GroepSolosSTS: String,
            val ConcertoSTS: String,
            val FaceToFace: Boolean
        )
        // Replace with your data
        val userData = DataClass(
            vanneEnName,
            SkoolGraad,
            Instrument,
            BKSTS,
            RMSTS,
            GroepSolosSTS,
            ConcertoSTS,
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
        Toast.makeText(this@Page35, message, Toast.LENGTH_SHORT).show()
    }
}