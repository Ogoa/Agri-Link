package com.example.agri_link.ui.screens

import LoginForm
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.agri_link.ui.state_data_classes.SignInState

@Composable
fun SignInScreen(
    state: SignInState,
    googleSignIn: () -> Unit,
    signUp: () -> Unit
) {
    val context = LocalContext.current

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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        LoginForm(signInWithGoogle = googleSignIn, signUp = signUp)

        /*val viewModel = viewModel<NewAccountDetailsViewModel>()
        val State by viewModel.state.collectAsStateWithLifecycle()
        val fd by viewModel.fieldsDisabled.collectAsStateWithLifecycle()*/

    }
}