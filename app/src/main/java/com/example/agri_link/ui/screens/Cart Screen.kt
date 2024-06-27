package com.example.agri_link.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agri_link.ui.view_models.CartViewModel
import com.example.agri_link.ui.state_data_classes.CartItem

@Composable
fun CartScreen(viewModel: CartViewModel) {
    val cartItems by viewModel.cartItems.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Your Cart",
            modifier = Modifier.padding(vertical = 8.dp),
            style = TextStyle(fontSize = 24.sp)
        )

        LazyColumn {
            items(cartItems) { cartItem ->
                CartItemCard(cartItem = cartItem, viewModel = viewModel)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display total items in cart
        Text(
            text = "Total Items in Cart: ${cartItems.size}",
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // Checkout button
        Button(
            onClick = {
                //viewModel.checkout()
            },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text(text = "Checkout")
        }
    }
}

@Composable
fun CartItemCard(cartItem: CartItem, viewModel: CartViewModel) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        onClick = {
            // flip flop the expansion
            expanded = expanded.not()
        }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Product: ${cartItem.Name}")
            Text(text = "Description: ${cartItem.Description}")
            Text(text = "Price: KES ${cartItem.Price}")

            if (expanded) {
                Button(
                    onClick = {
                        viewModel.removeFromCart(cartItem)
                    },
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text(text = "Remove")
                }
            }
        }
    }
}

