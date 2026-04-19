package com.my.docscannerapp.viewmodels

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class pdfViewModel: ViewModel() {
    var isSplashScreen by mutableStateOf(false)

    init {
        viewModelScope.launch {
            delay(3000)
            isSplashScreen=false
        }
    }
}