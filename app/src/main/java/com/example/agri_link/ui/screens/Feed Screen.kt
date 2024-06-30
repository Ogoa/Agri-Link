package com.example.agri_link.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.agri_link.ui.view_models.FeedViewModel

@Composable
fun FeedScreen(
    viewModel: FeedViewModel,
) {
    val posts by viewModel.posts.collectAsState()

    Surface {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(posts) { post ->
                Text(
                    text = post.Name,
                    modifier = Modifier.padding(vertical = 8.dp),
                    style = TextStyle(
                        textDecoration = TextDecoration.Underline,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = post.Description,
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "KES ${post.Price}",
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = MaterialTheme.colorScheme.tertiary,
                    style = TextStyle(
                        textDecoration = TextDecoration.Underline,
                        fontWeight = FontWeight.Bold
                    )
                )

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(post.Pictures) { image ->
                        AsyncImage(
                            model = image,
                            contentDescription = "Post Image",
                            modifier = Modifier
                                .size(300.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                // TODO prevent double adding
                // Add Product to Cart
                Button(
                    onClick = {
                        viewModel.addToCart(post)
                    },
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text(text = "Add to Cart")
                }

                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}
