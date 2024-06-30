package com.example.agri_link.ui.screens

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Sell
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.agri_link.firebase_functions.uploadImagesToFirebaseStorageAndFirestore
import com.example.agri_link.ui.view_models.UploadProductsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun PostProduct(viewModel: UploadProductsViewModel) {
    // variables to store entered details

    var productPrice by remember { mutableStateOf("") }
    var imageUris by remember { mutableStateOf<List<Uri>>(emptyList()) }

    val context = LocalContext.current
    val bitmapsMap = remember { mutableStateMapOf<Uri, Bitmap>() }
    val lifecycleOwner = LocalLifecycleOwner.current

    val state = viewModel.state.collectAsState()

    // image picking dialog
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetMultipleContents()) { uris: List<Uri>? ->
            imageUris = uris ?: emptyList()
        }

    Surface(
        modifier = Modifier.fillMaxWidth(),
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = "Enter Product Details",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            item {
                // Product Name Text Field
                OutlinedTextField(
                    value = state.value.Name,
                    label = {
                        Text(text = "Product Name")
                    },
                    placeholder = {
                        Text(text = "Enter name of product")
                    },
                    onValueChange = {
                        viewModel.onNameChanged(it)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    //.weight(1f),
                    textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "product name icon"
                        )
                    },
                    singleLine = true,
                    visualTransformation = VisualTransformation.None,
                    keyboardActions = KeyboardActions.Default,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next,
                        capitalization = KeyboardCapitalization.Sentences
                    ),
                    shape = MaterialTheme.shapes.small
                )
            }

            item {
                // Product Description Text Field
                OutlinedTextField(
                    value = state.value.Description,
                    label = {
                        Text(text = "Description")
                    },
                    placeholder = {
                        Text(text = "Describe your product")
                    },
                    onValueChange = {
                        viewModel.onDescriptionChanged(it)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    //.weight(1f),
                    textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Description,
                            contentDescription = "description icon"
                        )
                    },
                    singleLine = false,
                    maxLines = 10,
                    visualTransformation = VisualTransformation.None,
                    keyboardActions = KeyboardActions.Default,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next,
                        capitalization = KeyboardCapitalization.Sentences
                    ),
                    shape = MaterialTheme.shapes.small
                )
            }

            item {
                // Product Price
                OutlinedTextField(
                    value = productPrice,
                    label = {
                        Text(text = "Price")
                    },
                    placeholder = {
                        Text(text = "Enter a suitable price")
                    },
                    onValueChange = {
                        productPrice = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    //.weight(1f),
                    textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Sell,
                            contentDescription = "price icon"
                        )
                    },
                    singleLine = true,
                    visualTransformation = VisualTransformation.None,
                    keyboardActions = KeyboardActions.Default,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    shape = MaterialTheme.shapes.small
                )
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }

            item {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(imageUris) { uri ->
                        val bitmap = bitmapsMap[uri]

                        // display the image
                        if (bitmap != null) {
                            Image(
                                bitmap = bitmap.asImageBitmap(),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(300.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            LaunchedEffect(uri) {
                                // Offload bitmap decoding to a background thread
                                lifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                                    try {
                                        // decode the image
                                        val decodedBitmap = if (Build.VERSION.SDK_INT >= 28) {
                                            val source =
                                                ImageDecoder.createSource(
                                                    context.contentResolver,
                                                    uri
                                                )
                                            ImageDecoder.decodeBitmap(source)
                                        } else {
                                            @Suppress("DEPRECATION")
                                            MediaStore.Images.Media.getBitmap(
                                                context.contentResolver,
                                                uri
                                            )
                                        }
                                        bitmapsMap[uri] = decodedBitmap
                                    } catch (e: Exception) {
                                        // Handle exception
                                    }
                                }
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))
            }

            item {
                Button(onClick = {
                    launcher.launch("image/*")
                }) {
                    Text(text = "Pick Images")
                }
            }

            item {
                Button(
                    onClick = {
                        viewModel.uploadPost(
                            uris = imageUris,
                            productPrice = productPrice.toInt()
                        )
                    }) {
                    Text(text = "Post")
                }
            }
        }
    }
}
