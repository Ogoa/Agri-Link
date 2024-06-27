package com.example.agri_link.firebase_functions

import android.net.Uri
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.storage.storage
import java.util.UUID

// uploads the images to Firebase Storage
fun uploadImagesToFirebaseStorageAndFirestore(
    uris: List<Uri>,
    onFailure: (Exception) -> Unit,
    productName: String,
    productDescription: String,
    productPrice: Int
) {
    // Storage reference
    val storageRef = Firebase.storage.reference
    // authenticated user's ID
    val userId = Firebase.auth.currentUser?.uid ?: "unknown_user"
    //list of Image URLs
    val imageURLs = mutableListOf<String>()

    uris.forEach { uri ->
        val imageId = UUID.randomUUID().toString()
        val imageRef = storageRef.child("images/test/$userId/$imageId")

        // Upload Images to Storage first
        imageRef.putFile(uri)
            .addOnSuccessListener {
                // get Image URL
                imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                    // URLs downloaded from Storage added to URLs list
                    imageURLs.add(downloadUri.toString())

                    // If all images have been uploaded to Storage...
                    if (imageURLs.size == uris.size) {
                        // Then upload Post to Firestore
                        addPostToFirestore(
                            productName = productName,
                            productDescription = productDescription,
                            imageURLs = imageURLs,
                            productPrice = productPrice
                        )
                    }
                }
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }
}
