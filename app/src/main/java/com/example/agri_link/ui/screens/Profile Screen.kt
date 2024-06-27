package com.example.agri_link.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agri_link.ui.state_data_classes.UserData



//@Destination
@Composable
fun ProfileScreen(
    userData: UserData?,
    //navigator: DestinationsNavigator,
    uploadImages: () -> Unit,
    feed: () -> Unit,
    cart: () -> Unit,
    onSignOut: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        /*if(userData?.profilePictureUrl != null) {
            AsyncImage(
                model = userData.profilePictureUrl,
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
        }*/


        /*if (userData?.profilePictureUrl != null) {
            Image(
                bitmap = userData.profilePictureUrl.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .padding(20.dp)
                    .clip(RectangleShape) // or any other shape you want
            )
        }*/


        if(userData?.username != null) {
            Text(
                text = userData.username,
                textAlign = TextAlign.Center,
                fontSize = 36.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        if(userData?.email != null) {
            Text(
                text = userData.email,
                textAlign = TextAlign.Center,
                fontSize = 36.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        /*if(userData?.phone != null) {
            Text(
                text = userData.phone.plus("ss"),
                textAlign = TextAlign.Center,
                fontSize = 36.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(16.dp))
        }*/


        Button(onClick = uploadImages) {
            Text(text = "Make a Post")
        }

        Button(onClick = feed) {
            Text(text = "FEED")
        }

        Button(onClick = cart) {
            Text(text = "CART")
        }

        Button(onClick = onSignOut) {
            Text(text = "Sign out")
        }

        //LoginForm()
    }
}