package com.example.agri_link.ui

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

@Composable
fun SignInScreen(
    state: SignInState,
    googleSignIn: () -> Unit
) {
    val context = LocalContext.current

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
        LoginForm(signInWithGoogle = googleSignIn)

        /*val viewModel = viewModel<NewAccountDetailsViewModel>()
        val State by viewModel.state.collectAsStateWithLifecycle()
        val fd by viewModel.fieldsDisabled.collectAsStateWithLifecycle()

        NewAccountDetails(viewModel = viewModel, state = State)*/
    }
}