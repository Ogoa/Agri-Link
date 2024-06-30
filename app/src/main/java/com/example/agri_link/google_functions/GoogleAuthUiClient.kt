package com.example.agri_link.google_functions

import com.example.agri_link.ui.state_data_classes.SignInResult
import com.example.agri_link.ui.state_data_classes.UserData
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.example.agri_link.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient

import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await

// wrapper around Google's One Tap sign-in functionality.
// It provides methods to handle sign-in, sign-out, and retrieval of the currently signed-in user's data.

// constructor of the GoogleAuthUiClient class.
// It takes a Context and a SignInClient as parameters.
// The Context is used to access application-specific resources and classes.
// The SignInClient is used to interact with Google's One Tap sign-in API.
class GoogleAuthUiClient(
    private val context: Context,
    private val oneTapClient: SignInClient
) {
    private val auth = Firebase.auth

    //initiates the sign-in process.
    // It builds a BeginSignInRequest and sends it to the SignInClient.
    // If the sign-in process is successful, it returns an IntentSender that can be used to start an activity for the result.
    // If an error occurs during the sign-in process, it prints the stack trace and returns null.
    suspend fun signIn(): IntentSender? {
        val result = try {
            oneTapClient.beginSignIn(
                buildSignInRequest()
            ).await()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            null
        }
        return result?.pendingIntent?.intentSender
    }

    // handles the result of the sign-in process.
    // It takes an Intent as a parameter, which contains the result of the sign-in process.
    // It retrieves the sign-in credentials from the Intent, signs in to Firebase with these credentials, and returns a SignInResult containing the user's data if the sign-in was successful, or an error message if it was not.
    suspend fun signInWithIntent(intent: Intent): SignInResult {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)

        return try {
            val user = auth.signInWithCredential(googleCredentials).await().user

            SignInResult(
                data = user?.run {
                    UserData(
                        userId = uid,
                        username = displayName!!,
                        profilePictureUrl = photoUrl?.toString() ?: "",
                        email = email ?: "dummyEmail",
                    )
                },
                errorMessage = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            SignInResult(
                data = null,
                errorMessage = e.message
            )
        }
    }

    // signs out the currently signed-in user.
    // It calls the signOut method on both the SignInClient and Firebase's Auth instance.
    suspend fun signOut() {
        try {
            oneTapClient.signOut().await()
            auth.signOut()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }

    // retrieves the data of the currently signed-in user.
    // It returns a UserData object containing the user's ID, username, profile picture URL, and email.
    // If no user is currently signed in, it returns null.
    fun getSignedInUser(): UserData? = auth.currentUser?.run {
        UserData(
            userId = uid,
            username = displayName!!,
            profilePictureUrl = photoUrl?.toString() ?: "",
            email = email ?: "dummyEmail",
        )
    }

    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.web_client_id))
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }
}