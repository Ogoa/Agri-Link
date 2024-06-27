package com.example.agri_link.ui.state_data_classes

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)