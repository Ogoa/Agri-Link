package com.example.agri_link.ui.state

data class UserData(
    val userId: String = "",
    val profilePictureUrl: String? = "",
    val username: String = "",
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val phone: String = "",
    val producer: Boolean = false
)