package com.example.agri_link.firebase_functions

import android.util.Log
import com.example.agri_link.ui.state_data_classes.CartItem
import com.example.agri_link.ui.state_data_classes.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await
import java.util.*

// This function is responsible for adding a product to the user's cart.
// It generates a unique ID for each product added to the cart
// and stores the product details in the user's cart collection in Firebase Firestore.
// The product details include the product's name, description, and price.
/**
 * Adds a product to the user's cart in Firebase Firestore.
 * @param post The product to be added to the cart.
 */
fun addToUserCart(post: Product, success: () -> Unit) {
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

    val userDoc = FirebaseFirestore.getInstance().collection("Users").document(userId)
    val userCart = userDoc.collection("Cart")

    Log.d("Firebase", "addToUserCart: In Function")

    // Generate a random UUID for the document ID
    // Add the post to the user's cart collection
    userCart.document(UUID.randomUUID().toString()).set(
        // Create a map containing the fields of the post
        hashMapOf(
            "ID" to UUID.randomUUID().toString(),
            "Name" to post.Name,
            "Description" to post.Description,
            "Price" to post.Price
        )
    )
        .addOnSuccessListener {
            Log.d("Firebase", "addToUserCart: Cart Document created")
            success()
        }
        .addOnFailureListener { e ->
            Log.w("Firebase", "addToUserCart: Error creating Cart Document", e)
        }
}

// retrieves all items in the user's cart from Firebase Firestore.
// It returns a list of CartItem objects, each representing a product in the user's cart.
/**
 * Retrieves all items in the user's cart from Firebase Firestore.
 * @return A list of CartItem objects, each representing a product in the user's cart.
 */
suspend fun retrieveCartItems(): MutableList<CartItem> {
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return mutableListOf()
    val userDoc = FirebaseFirestore.getInstance().collection("Users").document(userId)
    val userCart = userDoc.collection("Cart")

    val cartItems = mutableListOf<CartItem>()

    Log.d("Firebase", "retrieveCartItems: In Function")

    try {
        Log.d("Firebase", "retrieveCartItems: In try")

        userCart.get().await().documents.forEach{ cartItem ->
            val itemId = cartItem.id
            val itemData = cartItem.data

            Log.d("Firebase", "retrieveCartItems: document ${cartItem.id}")
            Log.d("Firebase", "retrieveCartItems: Cart Item Data: ${cartItem.id}")

            val item = cartItem.toObject<CartItem>()

            // Check for invalid documents
            if (item != null && item.ID.isNotEmpty() && item.Name.isNotEmpty() && item.Description.isNotEmpty() && item.Price != 0) {
                cartItems.add(item)
            } else {
                Log.w("Firebase", "retrieveCartItems: Invalid document: $cartItem.id")
            }
        }
    } catch (e: Exception) {
        Log.e("Firebase", "retrieveCartItems: Error retrieving cart items", e)
    }

    return cartItems
}

// removes a specific item from the user's cart in Firebase Firestore.
// The item to be removed is identified by its ID.
// If the item is found in the user's cart, it is deleted.
/**
 * Removes a specific item from the user's cart in Firebase Firestore.
 * @param itemId The ID of the item to be removed.
 */
suspend fun removeFromUserCart(itemId: String) {
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
    val userDoc = FirebaseFirestore.getInstance().collection("Users").document(userId)
    val userCart = userDoc.collection("Cart")

    Log.d("Firebase", "removeFromUserCart: In Function")

    try {
        Log.d("Firebase", "removeFromUserCart: In try")

        val querySnapshot = userCart.whereEqualTo("ID", itemId).get().await()

        for (document in querySnapshot.documents) {
            userCart.document(document.id).delete().await()

            Log.d("Firebase", "Item removed from cart: $itemId")

            return
        }
    } catch (e: Exception) {
        Log.e("Firebase", "Error removing item from cart: $itemId", e)
    }
}

