package com.example.kae

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object FirebaseUtils {

    // Define a reference to the database
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    // Unused, a root *node* reference
    private val reference: DatabaseReference = database.getReference()


    // Function to Write Child's Name to Teacher's Profile when creating a child's submission - creating a link
    fun writeChildToTeacher(vanneEnName: String) {

        // Get teacher's persisting ID from TeacherIdHolder
        val teacherId = TeacherIdHolder.teacherId
        // Specify path using child() method with the next key
        if (!teacherId.isNullOrBlank()) {
            val teacherNodeRef = reference.child("Teachers").child(teacherId)

            // Check if "numChildren" exists
            teacherNodeRef.child("numChildren").addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // "numChildren" exists, retrieve its value
                        val numChildren = dataSnapshot.getValue(Int::class.java) ?: 0
                        // Create the new "child" field and increase "numChildren"
                        val newChildField = "child ${numChildren + 1}"
                        teacherNodeRef.child(newChildField).setValue(vanneEnName)
                        teacherNodeRef.child("numChildren").setValue(numChildren + 1)
                    } else {
                        // "numChildren" doesn't exist, create it and set to 1
                        teacherNodeRef.child("numChildren").setValue(1)
                        teacherNodeRef.child("child 1").setValue(vanneEnName)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle error
                }
            })
        }
    }
}