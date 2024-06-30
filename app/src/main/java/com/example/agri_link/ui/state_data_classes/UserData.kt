package com.example.agri_link.ui.state_data_classes

// This data class represents a user of the application.
// userId is a unique identifier for the user.
// profilePictureUrl is the URL to the user's profile picture.
// username is the user's username.
// email is the user's email address.
// firstName is the user's first name.
// lastName is the user's last name.
// phone is the user's phone number.
data class UserData(
    val userId: String = "",
    val profilePictureUrl: String? = "",
    val username: String = "",
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val phone: String = "",
)