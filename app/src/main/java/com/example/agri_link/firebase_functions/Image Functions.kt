package com.example.agri_link.firebase_functions

import android.net.Uri
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.storage.storage
import java.util.UUID

// uploads the images to Firebase Storage
// It takes a list of URIs representing the images and the details of a product.
// For each URI, it generates a unique ID and creates a reference to a location in Firebase Storage.
// It then uploads the image to this location.
// Once the image is successfully uploaded, it retrieves the download URL of the image and adds it to a list.
// When all images have been uploaded and their URLs retrieved,
// it calls the addPostToFirestore function to create a new post in Firebase Firestore with the product details and image URLs.
/**
 * Uploads images to Firebase Storage and creates a new post in Firebase Firestore with the product details and image URLs.
 * @param uris The URIs of the images to be uploaded.
 * @param productName The name of the product.
 * @param productDescription The description of the product.
 * @param productPrice The price of the product.
 */
fun uploadImagesToFirebaseStorageAndFirestore(
    uris: List<Uri>,
    productName: String,
    productDescription: String,
    productPrice: Int,
    onPostSuccess: () -> Unit
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
                            productPrice = productPrice,
                            onPostSuccess = { onPostSuccess() }
                        )
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Firebase", "Failed: ${exception.message}")
            }
    }
}
