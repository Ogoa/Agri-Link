package com.example.agri_link.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestStoragePermission(
    permission: String,
    onPermissionGranted: @Composable () -> Unit
) {
    val permissionState = rememberPermissionState(permission)

    Log.d("Image Test", "RequestStoragePermission: Start Function")

    LaunchedEffect(Unit) {
        if (!permissionState.status.isGranted) {
            permissionState.launchPermissionRequest()

            Log.d("Image Test", "RequestStoragePermission: Launched Effect")
        }
    }

    if (permissionState.status.isGranted) {
        Log.d("Image Test", "RequestStoragePermission: Permission Granted")

        onPermissionGranted()
    } else {

        Log.d("Image Test", "RequestStoragePermission: Else Block")

        //Text("Storage permission is required to upload images.")
        Column {
            Text("Storage permission is required to upload images.")
            if (permissionState.status.shouldShowRationale || !permissionState.status.isGranted) {
                Log.d("Image Test", "RequestStoragePermission: If Block")


                Button(onClick = {
                    Log.d("Image Test", "RequestStoragePermission: Button Clicked")

                    permissionState.launchPermissionRequest()
                }) {
                    Text("Request Permission")
                }


            }
        }
    }
}

/*
@Composable
fun ImagePicker(onImagePicked: (Uri) -> Unit) {
    Log.d("Image Test", "ImagePicker: In Function")

    val context = LocalContext.current
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { onImagePicked(it) }
        }

    Button(onClick = { launcher.launch("image/*") }) {
        Text(text = "Select Image")
    }
}


@Composable
fun ImageUploadScreen() {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var uploadSuccess by remember { mutableStateOf<Boolean?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Log.d("Image Test", "ImageUploadScreen: In Function")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        RequestStoragePermission(Manifest.permission.READ_EXTERNAL_STORAGE) {
            ImagePicker(onImagePicked = { uri ->
                imageUri = uri
                uploadSuccess = null
                errorMessage = null
            })
        }

        Spacer(modifier = Modifier.height(16.dp))

        imageUri?.let {
            Text("Selected Image: ${it.path}")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                uploadImageToFirebaseStorage(it,
                    onSuccess = { imageUrl ->
                        val metadata = mapOf(
                            "name" to "Sample Image",
                            "description" to "Description of the image",
                            "timestamp" to FieldValue.serverTimestamp()
                        )
                        saveImageMetadataToFirestore(imageUrl, metadata,
                            onSuccess = {
                                uploadSuccess = true
                            },
                            onFailure = { exception ->
                                errorMessage = "Failed to save metadata: ${exception.message}"
                            }
                        )
                    },
                    onFailure = { exception ->
                        errorMessage = "Image upload failed: ${exception.message}"
                    }
                )
            }) {
                Text("Upload Image")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        when {
            uploadSuccess == true -> Text("Image uploaded successfully!")
            uploadSuccess == false -> Text("Image upload failed.")
            errorMessage != null -> Text("Error: $errorMessage")
        }
    }
}




fun saveImageMetadataToFirestore(
    imageUrl: String,
    metadata: Map<String, Any>,
    onSuccess: () -> Unit,
    onFailure: (Exception) -> Unit
) {
    val firestore = Firebase.firestore

    firestore.collection("images")
        .add(metadata.plus("url" to imageUrl))
        .addOnSuccessListener { onSuccess() }
        .addOnFailureListener { exception -> onFailure(exception) }
}


// WORKS
@Composable
fun PickImageFromGallery() {

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

    val lifecycleOwner = LocalLifecycleOwner.current

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
        }



    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // ToDo Implement Lazy Row to show multiple images
        // ToDo Clickable images to show X remove
        item {
            imageUri?.let {
                // Offload bitmap decoding to a background thread
                lifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                    try {
                        bitmap.value = if (Build.VERSION.SDK_INT >= 28) {
                            val source = ImageDecoder.createSource(context.contentResolver, it)
                            ImageDecoder.decodeBitmap(source)
                        } else {
                            @Suppress("DEPRECATION")
                            MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                        }
                    } catch (e: Exception) {
                        // Handle exception
                    }
                }

                bitmap.value?.let { btm ->
                    Image(
                        bitmap = btm.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            //.size(500.dp)
                            .padding(20.dp)
                    )
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
                Text(text = "Pick Image")
            }
        }

    }
}


@Composable
fun PickMultipleImagesFromGalleryV1() {
    var imageUris by remember { mutableStateOf<List<Uri>>(emptyList()) }
    val context = LocalContext.current
    val bitmaps = remember { mutableStateOf<List<Bitmap>>(emptyList()) }

    val lifecycleOwner = LocalLifecycleOwner.current

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetMultipleContents()) { uris: List<Uri>? ->
            imageUris = uris ?: emptyList()
        }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(imageUris) { uri ->
            var bitmap by remember { mutableStateOf<Bitmap?>(null) }

            LaunchedEffect(uri) {
                // Offload bitmap decoding to a background thread
                lifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                    try {
                        bitmap = if (Build.VERSION.SDK_INT >= 28) {
                            val source = ImageDecoder.createSource(context.contentResolver, uri)
                            ImageDecoder.decodeBitmap(source)
                        } else {
                            // for older SDK versions
                            @Suppress("DEPRECATION")
                            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                        }
                    } catch (e: Exception) {
                        // Handle exception
                    }
                }
            }

            bitmap?.let { btm ->
                Image(
                    bitmap = btm.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(20.dp)
                )
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
    }
}*/
*/
*/
 */




