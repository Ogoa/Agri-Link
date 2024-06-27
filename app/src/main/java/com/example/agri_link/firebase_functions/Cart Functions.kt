package com.example.agri_link.firebase_functions

import android.util.Log
import com.example.agri_link.ui.state_data_classes.CartItem
import com.example.agri_link.ui.state_data_classes.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await
import java.util.*

fun addToUserCart(post: Product) {
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
        }
        .addOnFailureListener { e ->
            Log.w("Firebase", "addToUserCart: Error creating Cart Document", e)
        }
}

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

            // Ensure itemData is not null and contains necessary fields
            /*if (itemData != null) {
                val itemName = itemData["Name"] as? String ?: ""
                val itemDescription = itemData["Description"] as? String ?: ""
                val itemPrice = (itemData["Price"] as? Int) ?: 0

                // Create CartItem object and add to list
                val cartItem = CartItem(
                    id = itemId,
                    name = itemName,
                    description = itemDescription,
                    price = itemPrice
                )

                cartItems.add(cartItem)
            } else {
                Log.w("Firestore", "retrieveCartItems: Invalid document: $itemId")
            }*/
        }
    } catch (e: Exception) {
        Log.e("Firebase", "retrieveCartItems: Error retrieving cart items", e)
    }

    return cartItems
}

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

