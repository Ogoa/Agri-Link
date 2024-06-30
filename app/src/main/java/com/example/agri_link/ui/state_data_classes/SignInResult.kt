package com.example.agri_link.ui.state_data_classes

// This data class represents the result of a sign-in attempt.
// data is a UserData object that contains information about the signed-in user
// errorMessage is a string that contains an error message if the sign-in attempt was unsuccessful.
data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)

