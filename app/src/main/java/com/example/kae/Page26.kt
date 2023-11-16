package com.example.kae

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Page26 : AppCompatActivity() {

    // Define a reference to the database
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    // Unused, a root *node* reference
    private val reference: DatabaseReference = database.getReference()

    private lateinit var vanneNameEditText: EditText
    private lateinit var MSEditText: EditText
    private lateinit var SkoolGraadEditText: EditText
    private lateinit var AfrLiriekeEditText: EditText
    private lateinit var EieKeuseSSEditText: EditText
    private lateinit var VolksSSEditText: EditText
    private lateinit var BarAriaEditText: EditText
    private lateinit var UitSSEditText: EditText
    private lateinit var BlySSEditText: EditText
    private lateinit var OpAriaEditText: EditText
    private lateinit var KunsLiedEditText: EditText
    private lateinit var RocknPopEditText: EditText
    private lateinit var FaceToFaceCheckBox: CheckBox
    private lateinit var VideoCheckBox: CheckBox
    private var FaceToFace: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page26)
        //
        val entryDescription = "Sang Senior Solo"
        vanneNameEditText = findViewById(R.id.editTextText119)
        MSEditText = findViewById(R.id.editTextText120)
        SkoolGraadEditText = findViewById(R.id.editTextText118)
        AfrLiriekeEditText = findViewById(R.id.editTextText112)
        EieKeuseSSEditText = findViewById(R.id.editTextText117)
        VolksSSEditText = findViewById(R.id.editTextText121)
        BarAriaEditText = findViewById(R.id.editTextText124)
        UitSSEditText = findViewById(R.id.editTextText122)
        BlySSEditText = findViewById(R.id.editTextText123)
        OpAriaEditText = findViewById(R.id.editTextText125)
        KunsLiedEditText = findViewById(R.id.editTextText113)
        RocknPopEditText = findViewById(R.id.editTextText126)
        FaceToFaceCheckBox = findViewById(R.id.checkBox44)
        VideoCheckBox = findViewById(R.id.checkBox43)
        val button: Button = findViewById(R.id.button20)
        //
        //SUBMIT BUTTON
        button.setOnClickListener {
            //
            val vanneEnName = vanneNameEditText.text.toString()
            val MS = MSEditText.text.toString()
            val SkoolGraad = SkoolGraadEditText.text.toString()
            val AfrLirieke = AfrLiriekeEditText.text.toString()
            val EieKeuseSS = EieKeuseSSEditText.text.toString()
            val VolksSS = VolksSSEditText.text.toString()
            val BarAria = BarAriaEditText.text.toString()
            val UitSS = UitSSEditText.text.toString()
            val BlySS = BlySSEditText.text.toString()
            val OpAria = OpAriaEditText.text.toString()
            val KunsLied = KunsLiedEditText.text.toString()
            val RocknPop = RocknPopEditText.text.toString()
            //

            // Check if both checkboxes are checked
            if (FaceToFaceCheckBox.isChecked && VideoCheckBox.isChecked) {
                showToast("Please select only one option (Face-To-Face or Video)")
            }
            //FaceToFace is checked
            else if (FaceToFaceCheckBox.isChecked) {
                FaceToFace=true
                submitDataToFirebase(entryDescription,vanneEnName, MS, SkoolGraad, AfrLirieke, EieKeuseSS, VolksSS, BarAria, UitSS, BlySS, OpAria, KunsLied, RocknPop, FaceToFace)
            }
            //Video is checked
            else if (VideoCheckBox.isChecked) {
                FaceToFace=false
                submitDataToFirebase(entryDescription,vanneEnName, MS, SkoolGraad, AfrLirieke, EieKeuseSS, VolksSS, BarAria, UitSS, BlySS, OpAria, KunsLied, RocknPop, FaceToFace)
            }
            // If neither checkbox is checked
            else {
                showToast("Please select either Face-To-Face or Video")
            }
        }
    }

    // Function To Submit Data To Firebase
    private fun submitDataToFirebase(description: String, vanneEnName: String, MS: String, SkoolGraad: String, AfrLirieke: String, EieKeuseSS: String, VolksSS: String, BarAria: String, UitSS: String, BlySS: String, OpAria: String, KunsLied: String, RocknPop: String, FaceToFace: Boolean) {
        data class DataClass(
            val vanneEnName: String,
            val MS: String,
            val SkoolGraad: String,
            val AfrLirieke: String,
            val EieKeuseSS: String,
            val VolksSS: String,
            val BarAria: String,
            val UitSS: String,
            val BlySS: String,
            val OpAria: String,
            val KunsLied: String,
            val RocknPop: String,
            val FaceToFace: Boolean
        )
        // Replace with your data
        val userData = DataClass(
            vanneEnName,
            MS,
            SkoolGraad,
            AfrLirieke,
            EieKeuseSS,
            VolksSS,
            BarAria,
            UitSS,
            BlySS,
            OpAria,
            KunsLied,
            RocknPop,
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
        Toast.makeText(this@Page26, message, Toast.LENGTH_SHORT).show()
    }
}