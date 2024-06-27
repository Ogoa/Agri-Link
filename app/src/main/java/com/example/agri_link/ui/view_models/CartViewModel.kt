package com.example.agri_link.ui.view_models
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agri_link.firebase_functions.removeFromUserCart
import com.example.agri_link.firebase_functions.retrieveCartItems
import com.example.agri_link.ui.state_data_classes.CartItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems = _cartItems.asStateFlow()

    init {
        loadCartItems()
    }

    private fun loadCartItems() {
        Log.d("View Model", "CartViewModel: loadCartItems -> In Function")

        viewModelScope.launch {
            try {
                Log.d("View Model", "CartViewModel: loadCartItems -> In try")

                val items = retrieveCartItems()
                _cartItems.value = items
            } catch (e: Exception) {
                Log.e("View Model", "CartViewModel: loadCartItems -> Error loading cart items", e)
            }
        }
    }

    fun removeFromCart(cartItem: CartItem) {
        Log.d("View Model", "CartViewModel: removeFromCart -> In Function")

        viewModelScope.launch {
            try {
                Log.d("View Model", "CartViewModel: removeFromCart -> In try")

                removeFromUserCart(cartItem.ID)
                // Remove the item from local state after successful removal
                _cartItems.value = _cartItems.value.filter { it.ID != cartItem.ID }
            } catch (e: Exception) {
                Log.e("View Model", "CartViewModel: removeFromCart -> Error removing item from cart", e)
            }
        }
    }

    /*fun checkout() {
        viewModelScope.launch {
            try {
                // Move items to checkout collection in Firestore
                //val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return@launch
                val userDoc = FirebaseFirestore.getInstance().collection("Users").document(userId)
                val userCheckout = userDoc.collection("Checkout")

                _cartItems.value.forEach { cartItem ->
                    userCheckout.document(cartItem.ID).set(
                        hashMapOf(
                            "Name" to cartItem.Name,
                            "Description" to cartItem.Description,
                            "Price" to cartItem.Price
                        )
                    )
                }

                // Clear cart items after successful checkout
                _cartItems.value = emptyList()

                // Optionally, remove items from the cart collection after checkout
                _cartItems.value.forEach { cartItem ->
                    removeFromUserCart(cartItem.ID)
                }
            } catch (e: Exception) {
                Log.e("CartViewModel", "Error during checkout", e)
            }
        }
    }*/
}
