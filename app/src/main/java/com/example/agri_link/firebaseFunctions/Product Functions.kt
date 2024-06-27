package com.example.agri_link.firebaseFunctions

import android.util.Log
import com.example.agri_link.ui.state.Product
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime

fun addPostToFirestore(
    productName: String,
    productDescription: String,
    imageURLs: List<String>,
    productPrice: Int
) {
    Log.d("Firebase Post To Document", "addPostToFirestore: In Function")

    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

    val userDoc = Firebase.firestore.collection("Users").document(userId)
    val userPosts = userDoc.collection("Posts")

    Log.d("Firebase", "addPostToFirestore: Setting...")

    // TODO make doc ID current date time
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
        }
        .addOnFailureListener { e ->
            Log.w("Firebase", "addPostToFirestore: Error creating Post Document", e)
        }
}

// This function retrieves documents from the "Users" collection,
// then retrieves the "Posts" sub-collection for each user document,
// and finally checks if the documents are valid before adding them to the userPosts list.
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