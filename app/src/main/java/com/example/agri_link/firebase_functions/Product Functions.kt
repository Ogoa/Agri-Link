package com.example.agri_link.firebase_functions

import android.util.Log
import com.example.agri_link.ui.state_data_classes.Product
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime

// adds a new post to Firebase Firestore.
// It takes the details of a product and a list of image URLs.
// It generates a unique ID for the post based on the current date and time,
// and creates a new document in the "Posts" sub-collection of the user's document in the "Users" collection.
// The document contains the product details and image URLs.
/**
 * Adds a new post to Firebase Firestore with the product details and image URLs.
 * @param productName The name of the product.
 * @param productDescription The description of the product.
 * @param imageURLs The URLs of the images of the product.
 * @param productPrice The price of the product.
 */
fun addPostToFirestore(
    productName: String,
    productDescription: String,
    imageURLs: List<String>,
    productPrice: Int,
    onPostSuccess: () -> Unit
) {
    Log.d("Firebase Post To Document", "addPostToFirestore: In Function")

    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

    val userDoc = Firebase.firestore.collection("Users").document(userId)
    val userPosts = userDoc.collection("Posts")

    Log.d("Firebase", "addPostToFirestore: Setting...")

    userPosts.document(LocalDateTime.now().toString()).set(
        hashMapOf(
            "Name" to productName,
            "Description" to productDescription,
            "Pictures" to imageURLs,
            "Price" to productPrice
        )
    )
        .addOnSuccessListener {
            Log.d("Firebase", "addPostToFirestore: Post Document created")
            onPostSuccess()
        }
        .addOnFailureListener { e ->
            Log.w("Firebase", "addPostToFirestore: Error creating Post Document", e)
        }
}

// retrieves all posts from the "Posts" sub-collection of each user document in the "Users" collection in Firebase Firestore.
// It returns a list of Product objects, each representing a post.
// The function checks for invalid documents and only adds valid posts to the list.
/**
 * Retrieves all posts from the "Posts" sub-collection of each user document in the "Users" collection in Firebase Firestore.
 * @return A list of Product objects, each representing a post.
 */
suspend fun retrieveFeedPosts(): MutableList<Product> {
    val usersCollection = Firebase.firestore.collection("Users")
    val userPosts = mutableListOf<Product>()

    Log.d("Firebase", "retrieveFeedPosts: In function")

    try {
        Log.d("Firebase", "retrieveFeedPosts: In try")

        usersCollection.get().await().documents.forEach { usersDocument ->
            Log.d("Firebase", "retrieveFeedPosts: User Doc ${usersDocument.id}")

            usersDocument.reference.collection("Posts").get().await().documents.forEach { post ->
                Log.d("Firebase", "retrieveFeedPosts: Post ${post.id}")

                val data = post.data
                Log.d("Firebase", "retrieveFeedPosts: Post Data: $data")

                //val feedPost = post.toObject(Product::class.java)

                val feedPost = post.toObject<Product>()

                // Check for invalid documents
                if (feedPost != null && feedPost.Name.isNotEmpty() && feedPost.Description.isNotEmpty() && feedPost.Price != 0) {
                    userPosts.add(feedPost)
                } else {
                    Log.w("Firebase", "retrieveFeedPosts: Invalid document: ${post.id}")
                }
            }
        }
    } catch (e: Exception) {
        Log.e("Firebase", "retrieveFeedPosts: Error retrieving posts", e)
        e.printStackTrace()
    }

    return userPosts
}