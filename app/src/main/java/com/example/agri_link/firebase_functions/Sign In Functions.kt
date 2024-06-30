package com.example.agri_link.firebase_functions

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint

// creates a new user document in Firebase Firestore if it does not already exist.
// The function first checks if a document for the logged in user already exists in the "Users" collection.
// If the document does not exist, it creates a new document with the user's ID as the document ID.
// It then sets the document's fields with the user's details.
// It also creates "Cart" and "Posts" sub-collections in the user's document.
/**
 * Creates a new user document in Firebase Firestore if it does not already exist.
 */
fun createUserDocumentIfNotExists() {
    val db = FirebaseFirestore.getInstance()
    val user = FirebaseAuth.getInstance().currentUser ?: return

    val docRef = db.collection("Users").document(user.uid)

    docRef.get()
        .addOnSuccessListener { document ->
            if (document.exists()) {
                Log.d(
                    "Firebase",
                    "createUserDocumentIfNotExists: User document ${user.uid} already exists"
                )

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
                    Log.d(
                        "Firebase",
                        "createUserDocumentIfNotExists: User document ${user.uid} created"
                    )

                    // Create Cart sub-collection
                    docRef.collection("Cart")
                        .document("IGNORE") // Create a new document since a collection must contain at least 1 document
                        .set(hashMapOf("placeholder" to true)) // Placeholder data
                        .addOnSuccessListener {
                            Log.d(
                                "Firebase",
                                "createUserDocumentIfNotExists: Cart sub-collection \"IGNORE\" created"
                            )
                        }
                        .addOnFailureListener { e ->
                            Log.w(
                                "Firebase",
                                "createUserDocumentIfNotExists: Error creating Cart sub-collection \"IGNORE\"",
                                e
                            )
                        }

                    // Create Posts sub-collection
                    docRef.collection("Posts")
                        .document("IGNORE") // Create a new document since a collection must contain at least 1 document
                        .set(hashMapOf("placeholder" to true)) // Placeholder data
                        .addOnSuccessListener {
                            Log.d(
                                "Firebase",
                                "createUserDocumentIfNotExists: Posts sub-collection \"IGNORE\" created"
                            )
                        }
                        .addOnFailureListener { e ->
                            Log.w(
                                "Firebase",
                                "createUserDocumentIfNotExists: Error creating Posts sub-collection \"IGNORE\"",
                                e
                            )
                        }
                }
                .addOnFailureListener { e ->
                    Log.w(
                        "Firebase",
                        "createUserDocumentIfNotExists: Error creating user document ${user.uid}",
                        e
                    )
                }
        }
        .addOnFailureListener { exception ->
            Log.w(
                "Firebase",
                "createUserDocumentIfNotExists: Error checking for user document ${user.uid}",
                exception
            )
        }
}