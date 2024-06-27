package com.example.agri_link.firebaseFunctions

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint

fun createUserDocumentIfNotExists(user: FirebaseUser) {
    val db = FirebaseFirestore.getInstance()
    val docRef = db.collection("Users").document(user.uid)

    docRef.get()
        .addOnSuccessListener { document ->
            if (document.exists()) {
                Log.d("Firebase Sign In", "User document ${user.uid} already exists")

                return@addOnSuccessListener
            }

            val newUser = hashMapOf(
                "Email" to user.email,
                "First Name" to "",
                "Last Name" to "",
                "Location GeoPoint" to GeoPoint(0.0, 0.0),
                "Location PlaceID" to "",
                "Phone" to if (user.phoneNumber == null) "" else user.phoneNumber
            )

            docRef.set(newUser)
                .addOnSuccessListener {
                    Log.d("Firebase Sign In", "User document ${user.uid} created")

                    // Create Images sub-collection
                    /*docRef.collection("Images")
                        .document("IGNORE") // Create a new document with a generated ID
                        .set(hashMapOf("placeholder" to true)) // Placeholder data
                        .addOnSuccessListener {
                            Log.d("Firebase Sign In", "Images sub-collection \"IGNORE\" created")
                        }
                        .addOnFailureListener { e ->
                            Log.w(
                                "Firebase Sign In",
                                "Error creating Images sub-collection \"IGNORE\"",
                                e
                            )
                        }*/

                    // Create Posts sub-collection
                    docRef.collection("Posts")
                        .document("IGNORE") // Create a new document with a generated ID
                        .set(hashMapOf("placeholder" to true)) // Placeholder data
                        .addOnSuccessListener {
                            Log.d("Firebase Sign In", "Posts sub-collection \"IGNORE\" created")
                        }
                        .addOnFailureListener { e ->
                            Log.w(
                                "Firebase Sign In",
                                "Error creating Posts sub-collection \"IGNORE\"",
                                e
                            )
                        }

                }
                .addOnFailureListener { e ->
                    Log.w("Firebase Sign In", "Error creating user document ${user.uid}", e)
                }

        }
        .addOnFailureListener { exception ->
            Log.w("Firebase Sign In", "Error checking for user document ${user.uid}", exception)
        }
}