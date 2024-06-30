package com.example.agri_link.ui.state_data_classes

// This data class represents an item in the user's shopping cart.
// ID is a unique identifier for the item
// Name is the name of the item
// Description provides details about the item
// Price is the cost of the item.
data class CartItem(
    val ID: String = "",
    val Name: String = "",
    val Description: String = "",
    val Price: Int = 0
)