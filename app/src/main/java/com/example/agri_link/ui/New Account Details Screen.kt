package com.example.agri_link.ui


import com.example.agri_link.ui.view_models.NewAccountDetailsViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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


@Composable
fun NewAccountDetails(state: UserData, viewModel: NewAccountDetailsViewModel) {

    val fieldsDisabled by viewModel.fieldsDisabled.collectAsState(initial = false)

    Surface(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        color = Color(0xFF171717),
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
            Text(
                text = "Account Details",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Text(
                text = "Choose account type",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                modifier = Modifier.padding(top = 16.dp)
            )

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

            /*AccountDetailsForm(
                email = state.email,
                username = state.username,
                firstName = state.firstName,
                lastName = state.lastName,
                phone = state.phone,
                readOnly = false,
                onEmailChange = { viewModel.onEmailChanged(it) },
                onUsernameChange = { viewModel.onUsernameChanged(it) },
                onFirstNameChange = { viewModel.onFirstNameChanged(it) },
                onLastNameChange = { viewModel.onLastNameChanged(it) },
                onPhoneChange = { viewModel.onPhoneChanged(it) })*/


/////////////////////////////////////////////

            Surface(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                color = Color(0xFF171717),
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

                    // Email Address
                    /*OutlinedTextField(
                        value = state.email,
                        label = {
                            Text(text = "Email")
                        },
                        placeholder = {
                            Text(text = "Email Address")
                        },
                        onValueChange = { viewModel.onEmailChanged(it) },
                        modifier = Modifier.fillMaxWidth(),
                        //.weight(1f),
                        textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = "email icon"
                            )
                        },
                        readOnly = fieldsDisabled,
                        singleLine = true,
                        visualTransformation = VisualTransformation.None,
                        keyboardActions = KeyboardActions.Default,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Done
                        ),
                        shape = MaterialTheme.shapes.small

                    )*/


                }
            }


        }
    }
}
