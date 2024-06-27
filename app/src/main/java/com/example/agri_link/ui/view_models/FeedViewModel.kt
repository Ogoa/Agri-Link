package com.example.agri_link.ui.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agri_link.firebaseFunctions.retrieveFeedPosts
import com.example.agri_link.ui.state.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FeedViewModel : ViewModel() {
    private val _posts = MutableStateFlow<List<Product>>(listOf())
    val posts = _posts.asStateFlow()

    init {
        getPosts()
    }

    private fun getPosts() {
        viewModelScope.launch {
            val posts = retrieveFeedPosts()

            Log.d("retrieveFeedPosts", "In ViewModel")

            _posts.value = posts
        }
    }

    fun addToCart(post: Product) {
        viewModelScope.launch {
            try {
                //addToUserCart(post)
                Log.d("addToCart", "Added to cart: ${post.Name}")
            } catch (e: Exception) {
                Log.e("addToCart", "Error adding to cart", e)
            }
        }
    }
}