package com.example.agri_link.ui.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agri_link.firebase_functions.addToUserCart
import com.example.agri_link.firebase_functions.retrieveFeedPosts
import com.example.agri_link.ui.state_data_classes.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FeedViewModel : ViewModel() {
    private val _posts = MutableStateFlow<List<Product>>(listOf())
    val posts = _posts.asStateFlow()

    private val _addedToCart = MutableStateFlow(false)
    val addedToCart = _addedToCart.asStateFlow()

    init {
        getPosts()
    }

    private fun getPosts() {
        viewModelScope.launch {
            val posts = retrieveFeedPosts()

            Log.d("View Model", "FeedViewModel: retrieveFeedPosts ->In ViewModel")

            _posts.value = posts
        }
    }

    fun addToCart(post: Product) {
        try {
            addToUserCart(
                post, success = {
                    _addedToCart.value = true
                }
            )

            Log.d("View Model", "FeedViewModel: addToCart -> Added to cart: ${post.Name}")
        } catch (e: Exception) {
            Log.e("View Model", "FeedViewModel: addToCart -> Error adding to cart", e)
        }
    }

    fun falsifyAddedToCart() {
        _addedToCart.value = false
    }

}
