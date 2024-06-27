package com.example.agri_link.ui.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun AccountDetailsForm(
    email: String,
    username: String,
    firstName: String,
    lastName: String,
    phone: String,
    readOnly: Boolean = false,
    onEmailChange: (String) -> Unit,
    onUsernameChange: (String) -> Unit,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit
) {
    /*    var email by remember { mutableStateOf("") }
        var username by remember { mutableStateOf("") }
        var firstName by remember { mutableStateOf("") }
        var lastName by remember { mutableStateOf("") }
        var phone by remember { mutableStateOf("") }

    val emailReadOnly by remember { mutableStateOf(false) }
    val usernameReadOnly by remember { mutableStateOf(false) }
    val firstNameReadOnly by remember { mutableStateOf(false) }
    val lastNameReadOnly by remember { mutableStateOf(false) }
    val phoneReadOnly by remember { mutableStateOf(false) }
    */


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
                value = username,
                label = { Text(text = "Username") },
                placeholder = { Text(text = "Username") },
                onValueChange = { onUsernameChange(it) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Business,
                        contentDescription = null
                    )
                },
                readOnly = readOnly,
                minLines = 1,
                visualTransformation = VisualTransformation.None,
                keyboardActions = KeyboardActions.Default,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                shape = MaterialTheme.shapes.small,

                )

            // First Name
            OutlinedTextField(
                value = firstName,
                label = {
                    Text(text = "First Name")
                },
                placeholder = {
                    Text(text = "First Name")
                },
                onValueChange = { onFirstNameChange(it) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                readOnly = readOnly,
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                keyboardActions = KeyboardActions.Default,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                shape = MaterialTheme.shapes.small,

                )

            // Last Name
            OutlinedTextField(
                value = lastName,
                label = {
                    Text(text = "Last Name")
                },
                placeholder = {
                    Text(text = "Last Name")
                },
                onValueChange = { onLastNameChange(it) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                readOnly = readOnly,
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                keyboardActions = KeyboardActions.Default,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                shape = MaterialTheme.shapes.small,

                )

            // Phone Number
            OutlinedTextField(
                value = phone,
                label = {
                    Text(text = "Phone number")
                },
                placeholder = {
                    Text(text = "Phone Number")
                },
                onValueChange = { onPhoneChange(it) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                readOnly = readOnly,
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                keyboardActions = KeyboardActions.Default,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Done
                ),
                shape = MaterialTheme.shapes.small,

                )

            // Email Address
            OutlinedTextField(
                value = email,
                label = {
                    Text(text = "Email")
                },
                placeholder = {
                    Text(text = "Email Address")
                },
                onValueChange = { onEmailChange(it) },
                modifier = Modifier.fillMaxWidth(),
                //.weight(1f),
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "email icon"
                    )
                },
                readOnly = readOnly,
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                keyboardActions = KeyboardActions.Default,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done
                ),
                shape = MaterialTheme.shapes.small,

                )

            //////////////

            /*OutlinedTextField(
                value = password,
                label = {
                    Text(text = "Password")
                },
                placeholder = {
                    Text(text = "Password")
                },
                onValueChange = {
                    password = it
                },
                modifier = Modifier
                    //.weight(1f)
                    .fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = passwordVisible.not() }) {
                        Icon(
                            imageVector = if (passwordVisible)
                                Icons.Filled.Visibility
                            else Icons.Filled.VisibilityOff,
                            contentDescription = null
                        )
                    }
                },
                enabled = passwordEnabled,
                maxLines = 1,
                minLines = 1,
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardActions = KeyboardActions.Default,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                shape = MaterialTheme.shapes.small,
            )*/


        }
    }
}
