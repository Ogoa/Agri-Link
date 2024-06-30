package com.example.agri_link.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.agri_link.ui.state_data_classes.SignInState

@Preview
@Composable
fun SignInScreen(
    state: SignInState = SignInState(),
    googleSignIn: () -> Unit = fun() {},
    signUp: () -> Unit = fun() {}
) {
    val context = LocalContext.current

    //val colours = MaterialTheme.colorScheme

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var emailReadOnly by remember { mutableStateOf(false) }
    var passwordReadOnly by remember { mutableStateOf(false) }

    var passwordVisible by remember { mutableStateOf(false) }


    // Display a Toast with the Error Message if an error occurs
    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(30.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "Sign In",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                // Email Text Field
                OutlinedTextField(
                    value = email,
                    label = {
                        Text(text = "Email")
                    },
                    placeholder = {
                        Text(text = "Email Address")
                    },
                    onValueChange = {
                        email = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    //.weight(1f),
                    textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "email icon"
                        )
                    },
                    readOnly = emailReadOnly,
                    singleLine = true,
                    visualTransformation = VisualTransformation.None,
                    keyboardActions = KeyboardActions.Default,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    shape = MaterialTheme.shapes.small,

                    )

                //////////////

                OutlinedTextField(
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
                    readOnly = passwordReadOnly,
                    singleLine = true,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardActions = KeyboardActions.Default,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    shape = MaterialTheme.shapes.small,
                )




                Column(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Button(
                        /*colors = ButtonColors(
                            contentColor = colours.onPrimary,
                            containerColor = colours.primary,
                            disabledContentColor = colours.onPrimary,
                            disabledContainerColor = colours.inversePrimary
                        ),*/
                        onClick = { },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Sign In")
                    }


                    Button(
                        /*colors = ButtonColors(
                            contentColor = colours.onPrimary,
                            containerColor = colours.primary,
                            disabledContentColor = colours.onPrimary,
                            disabledContainerColor = colours.inversePrimary
                        ),*/
                        onClick = {
                            emailReadOnly = true
                            passwordReadOnly = true

                            googleSignIn()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Sign in with Google")
                    }
                }

                Row(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .padding(top = 16.dp)
                    ) {
                        Text("Forgot Password")
                    }

                    TextButton(
                        onClick = { signUp() },
                        modifier = Modifier
                            .padding(top = 16.dp)
                    ) {
                        Text("Sign Up")
                    }
                }
            }
        }
    }


    /*val viewModel = viewModel<NewAccountDetailsViewModel>()
    val State by viewModel.state.collectAsStateWithLifecycle()
    val fd by viewModel.fieldsDisabled.collectAsStateWithLifecycle()*/


}