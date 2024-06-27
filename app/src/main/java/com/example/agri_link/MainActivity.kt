package com.example.agri_link

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.agri_link.firebase_functions.createUserDocumentIfNotExists
import com.example.agri_link.google_functions.GoogleAuthUiClient
import com.example.agri_link.ui.screens.CartScreen
import com.example.agri_link.ui.screens.FeedScreen
import com.example.agri_link.ui.screens.NewAccountDetails
import com.example.agri_link.ui.screens.PostProduct
import com.example.agri_link.ui.screens.ProfileScreen
import com.example.agri_link.ui.screens.SignInScreen
import com.example.agri_link.ui.view_models.CartViewModel
import com.example.agri_link.ui.view_models.FeedViewModel
import com.example.agri_link.ui.view_models.SignInViewModel
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.libraries.places.api.Places
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

// entry point of the application
class MainActivity : ComponentActivity() {

    // Google Auth Client
    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // Define variables to hold the Places and Maps API keys.
        if (true) {
            val apiKey = BuildConfig.PLACES_API_KEY

            // Log an error if apiKey is not set.
            if (apiKey.isEmpty()) {
                Log.e("Places test", "No api key")
                finish()
                return
            } else {
                Log.d("Places test", "API key: $apiKey")
            }

            // Initialize the SDK
            Places.initializeWithNewPlacesApiEnabled(applicationContext, apiKey)

            // Create a new Places 653Client instance
            val placesClient = Places.createClient(this)

            Log.d("Places test", "Places Client: $placesClient")

            ////////////// Maps

            val mapsApiKey = BuildConfig.MAPS_API_KEY

            if (mapsApiKey.isEmpty()) {
                Log.e("Maps test", "No api key")
                finish()
                return
            } else {
                Log.d("Maps test", "API key: $mapsApiKey")
            }
        }


        setContent {
            // used to navigate between different screens
            val navController = rememberNavController()

            val showNavHost = true

            if (showNavHost) {
                // holds all the destinations/screens
                NavHost(
                    navController = navController,
                    startDestination = "sign_in"
                ) {
                    composable("sign_in") {
                        val viewModel = viewModel<SignInViewModel>()
                        val state by viewModel.state.collectAsStateWithLifecycle()

                        // checks if a user is signed in at app launch
                        LaunchedEffect(key1 = Unit) {
                            if (googleAuthUiClient.getSignedInUser() != null) {
                                // if a user is already signed in, navigate to another screen
                                navController.navigate("profile")
                            }
                        }

                        // launch an intent that brings up the Google Sign In Dialog
                        val launcher = rememberLauncherForActivityResult(
                            contract = ActivityResultContracts.StartIntentSenderForResult(),
                            onResult = { result ->
                                if (result.resultCode == RESULT_OK) {
                                    lifecycleScope.launch {
                                        val signInResult = googleAuthUiClient.signInWithIntent(
                                            intent = result.data ?: return@launch
                                        )
                                        viewModel.onSignInResult(signInResult)
                                    }
                                }
                            }
                        )

                        // on successful sign in:
                        LaunchedEffect(key1 = state.isSignInSuccessful) {
                            if (state.isSignInSuccessful) {
                                Toast.makeText(
                                    applicationContext,
                                    "Sign in successful",
                                    Toast.LENGTH_LONG
                                ).show()

                                // TODO move to ViewModel
                                createUserDocumentIfNotExists(FirebaseAuth.getInstance().currentUser!!)

                                navController.navigate("profile")
                                viewModel.resetState()
                            }
                        }

                        SignInScreen(
                            state = state,
                            googleSignIn = {
                                lifecycleScope.launch {
                                    val signInIntentSender = googleAuthUiClient.signIn()
                                    launcher.launch(
                                        IntentSenderRequest.Builder(
                                            signInIntentSender ?: return@launch
                                        ).build()
                                    )
                                }
                            },
                            signUp = {
                                navController.navigate("sign_up")
                            }
                        )
                    }

                    composable("sign_up") {
                        NewAccountDetails()
                    }

                    composable("upload_images") {
                        PostProduct()
                    }

                    composable("feed") {
                        val viewModel = viewModel<FeedViewModel>()
                        FeedScreen(viewModel = viewModel)
                    }

                    // Cart Screen
                    composable("cart") {
                        val viewModel = viewModel<CartViewModel>()
                        CartScreen(viewModel = viewModel)
                    }

                    composable("profile") {
                        ProfileScreen(
                            userData = googleAuthUiClient.getSignedInUser(),
                            uploadImages = {
                                navController.navigate("upload_images")
                            },
                            feed = {
                                navController.navigate("feed")
                            },
                            cart = {
                                navController.navigate("cart")
                            },
                            onSignOut = {
                                lifecycleScope.launch {
                                    googleAuthUiClient.signOut()
                                    Toast.makeText(
                                        applicationContext,
                                        "Signed out",
                                        Toast.LENGTH_LONG
                                    ).show()

                                    navController.navigate("sign_in") //.popBackStack()
                                    //navController.popBackStack()
                                }
                            }
                        )
                    }
                }
            } else {
                /*// TODO
                val viewModel = viewModel<NewAccountDetailsViewModel>()

                //NewAccountDetails(viewModel = viewModel)

                AddressAutocomplete(viewModel = viewModel, placesClient = placesClient)*/


                //ImageUploadScreen()

                //PickImageFromGallery()

                PostProduct()


            }


            //
        }
    }
}

