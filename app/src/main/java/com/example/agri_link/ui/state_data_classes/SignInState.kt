package com.example.agri_link.ui.state_data_classes

// This data class represents the state of a sign-in process.
// isSignInSuccessful is a boolean that indicates whether the sign-in was successful.
// signInError is a string that contains an error message if there was an error during the sign-in process.
data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)