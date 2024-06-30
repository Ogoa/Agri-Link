package com.example.agri_link.ui.view_models

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.agri_link.firebase_functions.uploadImagesToFirebaseStorageAndFirestore
import com.example.agri_link.ui.state_data_classes.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UploadProductsViewModel: ViewModel() {
    private val _state = MutableStateFlow(Product())
    val state = _state.asStateFlow()

    private val _postPosted = MutableStateFlow(false)
    val postPosted = _postPosted.asStateFlow()

    fun onProductUploadResult(product: Product) {
        _state.value = product
    }

    fun onNameChanged(name: String) {
        //_state.value = _state.value.copy(name = name)
        _state.update {
            it.copy(
                Name = name
            )
        }
    }

    fun onPicturesChanged(pictures: String){
        _state.update {
            it.copy(
                Pictures = listOf(pictures)
            )
        }
    }

    fun onDescriptionChanged(description: String){
        //_state.value = _state.value.copy(description = description)
        _state.update {
            it.copy(
                Description = description
            )
        }
    }

    fun onPriceChanged(price: Int){
        _state.update {
            it.copy(
                Price = price
            )
        }
    }

    fun uploadPost(
        uris: List<Uri>,
        productPrice: Int
    ){
        uploadImagesToFirebaseStorageAndFirestore(
            uris = uris,
            productName = _state.value.Name,
            productDescription = _state.value.Description,
            productPrice = productPrice,
            onPostSuccess = {
                _postPosted.value = true
            },
        )
    }

    fun falsifyPostPosted() {
        _postPosted.value = false
    }


}