package com.example.agri_link.ui.view_models

import com.example.agri_link.ui.UserData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NewAccountDetailsViewModel: ViewModel() {
    private val _state = MutableStateFlow(UserData())
    val state = _state.asStateFlow()

    val fieldsDisabled = MutableStateFlow(false)
    //val producerClicked = MutableStateFlow(false)



    fun fieldsDisableClicked(){
        fieldsDisabled.update { true }
    }

    fun producerClicked(){
        _state.update { it.copy(
            producer = true
        ) }
    }

    fun customerClicked(){
        _state.update { it.copy(
            producer = false
        ) }
    }

    fun onFirstNameChanged(firstName: String) {
        _state.update {
            it.copy(
                firstName = firstName
            )
        }


        /*sss.update {
            true
        }*/
    }

    fun onLastNameChanged(lastName: String) {
        _state.update {
            it.copy(
                lastName = lastName
            )
        }
    }

    fun onEmailChanged(email: String) {
        _state.update {
            it.copy(
                email = email
            )
        }
    }

    fun onUsernameChanged(username: String) {
        _state.update {
            it.copy(
                username = username
            )
        }
    }

    fun onPhoneChanged(phone: String) {
        _state.update {
            it.copy(
                phone = phone
            )
        }
    }



}