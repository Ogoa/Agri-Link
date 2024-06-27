package com.example.agri_link.ui.view_models

import android.content.ContentValues.TAG
import android.util.Log
import com.example.agri_link.ui.state_data_classes.UserData
import androidx.lifecycle.ViewModel
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.tasks.await

// TODO: Error Handling
// TODO: Input Validation

class NewAccountDetailsViewModel : ViewModel() {
    private val _state = MutableStateFlow(UserData())
    val state = _state.asStateFlow()

    val fieldsDisabled = MutableStateFlow(false)
    //val producerClicked = MutableStateFlow(false)


    fun disableFields() {
        fieldsDisabled.update { true }
    }

    fun producerClicked() {
        _state.update {
            it.copy(
                producer = true
            )
        }
    }

    fun customerClicked() {
        _state.update {
            it.copy(
                producer = false
            )
        }
    }

    fun onFirstNameChanged(firstName: String) {
        _state.update {
            it.copy(
                firstName = firstName
            )
        }
    }

    fun onLastNameChanged(lastName: String) {
        _state.update {
            it.copy(
                lastName = lastName
            )
        }
    }

    fun onEmailChanged(email: String) {
        _state.update {
            it.copy(
                email = email
            )
        }
    }

    fun onUsernameChanged(username: String) {
        _state.update {
            it.copy(
                username = username
            )
        }
    }

    fun onPhoneChanged(phone: String) {
        _state.update {
            it.copy(
                phone = phone
            )
        }
    }

    /**
     * This function is used to get autocomplete predictions for a given query using the Google Places API.
     * It creates a new autocomplete session token and a rectangular bounds object for the search area.
     * It then makes a request to the Places API to find autocomplete predictions.
     *
     * @param query The search query for which autocomplete predictions are to be fetched.
     * @param placesClient The PlacesClient instance used to interact with the Google Places API.
     *
     * @return A flow that emits a list of autocomplete predictions as strings.
     *
     * @throws ApiException if the Places API request fails.
     */
    suspend fun getAutocompletePredictions(query: String, placesClient: PlacesClient) = flow {
        // Create a new token for the autocomplete session
        val token = AutocompleteSessionToken.newInstance()

        // Create a RectangularBounds object
        val bounds = RectangularBounds.newInstance(
            LatLng(-4.6796, 39.1728), // Southwest corner (near Lunga Lunga: -4.6796, 39.1728)
            LatLng(3.9426, 41.8554) // Northeast corner (near Mandera: 3.9426, 41.8554)
        )

        // Use the Places API to find autocomplete predictions.
        val request = FindAutocompletePredictionsRequest.builder()
            // Call either setLocationBias() OR setLocationRestriction().
            .setLocationBias(bounds)
            //.setLocationRestriction(bounds)
            .setOrigin(LatLng(-1.286389, 36.817223)) // Nairobi, Kenya (-1.286389, 36.817223)
            .setCountries("KE")
            //.setTypesFilter(listOf(PlaceTypes.ADDRESS))
            .setSessionToken(token)
            .setQuery(query)
            .build()





        try {
            val response = placesClient.findAutocompletePredictions(request).await()
            val predictionList = mutableListOf<String>()
            for (prediction in response.autocompletePredictions) {
                predictionList.add(prediction.getFullText(null).toString())
                Log.d(
                    "Places Result",
                    "Successful search. Result = ${prediction.getFullText(null)}"
                )
            }
            emit(predictionList)
        } catch (exception: ApiException) {
            Log.e(TAG, "Place not found: " + exception.statusCode)
        }

    }

}