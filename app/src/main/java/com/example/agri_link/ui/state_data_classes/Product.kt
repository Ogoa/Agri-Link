package com.example.agri_link.ui.state_data_classes

// Leave as is!
// To match the document in Firestore
data class Product(
    val Pictures: List<String> = listOf(),
    val Description: String = "",
    val Price: Int = 0,
    val Name: String = ""
)
