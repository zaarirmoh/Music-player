package com.example.musicplayer

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MusicPlayerViewModel(
    private val context: Context
): ViewModel() {

    private val _isReady = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()

    private var showWelcomeScreen by mutableStateOf(false)

    init {
        viewModelScope.launch {
            val preferences = context.dataStore.data.first()
            showWelcomeScreen = preferences[SHOW_WELCOME_SCREEN] ?: false
            _isReady.value = true
        }
    }

    fun getStartingScreen(): String {
        return if (!showWelcomeScreen)
            "GetStartedScreen" else "HomeScreen"
    }

    fun changeStartingScreen() {
        viewModelScope.launch {
            context.dataStore.edit { settings ->
                settings[SHOW_WELCOME_SCREEN] = true
            }
        }

    }

}