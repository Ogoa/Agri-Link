package com.example.agri_link.ui.state_data_classes

// Leave as is!
// To match the document in Firestore

// This data class represents a product that is available for purchase.
// Pictures is a list of URLs to images of the product
// Description provides details about the product
// Price is the cost of the product
// Name is the name of the product.
data class Product(
    val Pictures: List<String> = listOf(),
    val Description: String = "",
    val Price: Int = 0,
    val Name: String = ""
)
