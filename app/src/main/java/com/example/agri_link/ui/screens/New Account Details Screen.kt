package com.example.agri_link.ui.screens

import androidx.compose.foundation.background
import com.example.agri_link.ui.view_models.NewAccountDetailsViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Switch
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@Preview
@Composable
fun NewAccountDetails(
    viewModel: NewAccountDetailsViewModel = viewModel<NewAccountDetailsViewModel>(),
) {


    val fieldsDisabled by viewModel.fieldsDisabled.collectAsState()
    val state by viewModel.state.collectAsState()

    Surface(
        modifier = Modifier
            //.padding(16.dp)
            .fillMaxSize(),
        color = Color(0xFF171717),
        //contentColor  = Color.White,
        // shadowElevation = 8.dp,
        //shape = MaterialTheme.shapes.large
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            item {
                Text(
                    text = "Account Details",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }

            item {
                Text(
                    text = "Choose account type",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            item {
                Row(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(
                        onClick = {
                            viewModel.producerClicked()
                        },
                        modifier = Modifier
                    ) {
                        Text("Producer")

                        Spacer(modifier = Modifier.width(5.dp))

                        if (state.producer) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null
                            )
                        }
                    }

                    Button(
                        onClick = {
                            viewModel.customerClicked()
                        },
                        modifier = Modifier
                    ) {
                        Text("Customer")

                        Spacer(modifier = Modifier.width(5.dp))

                        if (state.producer.not()) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null
                            )
                        }
                    }
                }
            }


/////////////////////////////////////////////

            item {
                Surface(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize(),
                    color = Color(0xFF1F1F1F),
                    //contentColor  = Color.White,
                    // shadowElevation = 8.dp,
                    shape = MaterialTheme.shapes.large
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {

                        // Username
                        OutlinedTextField(
                            value = state.username,
                            label = { Text(text = "Username") },
                            placeholder = { Text(text = "Username") },
                            onValueChange = { viewModel.onUsernameChanged(it) },
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Badge,
                                    contentDescription = null
                                )
                            },
                            readOnly = fieldsDisabled,
                            minLines = 1,
                            visualTransformation = VisualTransformation.None,
                            keyboardActions = KeyboardActions.Default,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                capitalization = KeyboardCapitalization.Words,
                                imeAction = ImeAction.Next
                            ),
                            shape = MaterialTheme.shapes.small,

                            )

                        // First Name
                        OutlinedTextField(
                            value = state.firstName,
                            label = {
                                Text(text = "First Name")
                            },
                            placeholder = {
                                Text(text = "First Name")
                            },
                            onValueChange = { viewModel.onFirstNameChanged(it) },
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                            readOnly = fieldsDisabled,
                            singleLine = true,
                            visualTransformation = VisualTransformation.None,
                            keyboardActions = KeyboardActions.Default,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                capitalization = KeyboardCapitalization.Words,
                                imeAction = ImeAction.Next
                            ),
                            shape = MaterialTheme.shapes.small

                        )

                        // Last Name
                        OutlinedTextField(
                            value = state.lastName,
                            label = {
                                Text(text = "Last Name")
                            },
                            placeholder = {
                                Text(text = "Last Name")
                            },
                            onValueChange = { viewModel.onLastNameChanged(it) },
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                            readOnly = fieldsDisabled,
                            singleLine = true,
                            visualTransformation = VisualTransformation.None,
                            keyboardActions = KeyboardActions.Default,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                capitalization = KeyboardCapitalization.Words,
                                imeAction = ImeAction.Next
                            ),
                            shape = MaterialTheme.shapes.small

                        )

                        // Phone Number
                        OutlinedTextField(
                            value = state.phone,
                            label = {
                                Text(text = "Phone number")
                            },
                            placeholder = {
                                Text(text = "Phone Number")
                            },
                            onValueChange = { viewModel.onPhoneChanged(it) },
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Smartphone,
                                    contentDescription = null
                                )
                            },
                            readOnly = fieldsDisabled,
                            singleLine = true,
                            visualTransformation = VisualTransformation.None,
                            keyboardActions = KeyboardActions.Default,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Phone,
                                imeAction = ImeAction.Done
                            ),
                            shape = MaterialTheme.shapes.small
                        )
                    }
                }
            }

            item {
                Text(
                    text = "Select your location",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            item {

            }

            item {
                val singapore = LatLng(1.35, 103.87)
                val cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(singapore, 10f)
                }
                Surface(
                    modifier = Modifier.aspectRatio(1f),
                    shape = MaterialTheme.shapes.medium,
                ) {
                    GoogleMap(
                        modifier = Modifier.fillMaxSize(),
                        cameraPositionState = cameraPositionState
                    ) {
                        Marker(
                            state = MarkerState(position = singapore),
                            title = "Singapore",
                            snippet = "Marker in Singapore"
                        )
                    }
                }
            }


        }
    }
}

@Composable
fun AddressAutocomplete(
    viewModel: NewAccountDetailsViewModel,
    placesClient: PlacesClient

) {
    var query by remember { mutableStateOf("") }
    var suggestions by remember { mutableStateOf(listOf<String>()) }
    var isDropdownExpanded by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current


    Surface(
        modifier = Modifier
            //.padding(16.dp)
            .fillMaxSize(),
        color = Color.White,
        //contentColor  = Color.White,
        // shadowElevation = 8.dp,
        //shape = MaterialTheme.shapes.large
    ) {
        Box {
            OutlinedTextField(
                value = query,
                onValueChange = { newValue ->
                    query = newValue
                    // Call the Places API to get suggestions
                    viewModel.viewModelScope.launch {
                        viewModel.getAutocompletePredictions(query, placesClient)
                            .collect { result ->
                                suggestions = result
                                isDropdownExpanded = suggestions.isNotEmpty()
                                keyboardController?.show() // Show the keyboard

                            }
                    }

                },
                label = { Text("Address") },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .onFocusChanged { focusState ->
                        isDropdownExpanded = focusState.isFocused && suggestions.isNotEmpty()
                    },

                )


            DropdownMenu(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopStart),
                expanded = isDropdownExpanded,
                onDismissRequest = {
                    isDropdownExpanded = false
                }
            ) {
                suggestions.forEach { suggestion ->
                    DropdownMenuItem(
                        text = { Text(text = suggestion) },
                        onClick = {
                            query = suggestion
                            isDropdownExpanded = false
                            focusManager.clearFocus(force = true)
                            focusRequester.requestFocus()
                            keyboardController?.show()
                        })
                }
            }

        }
    }
}