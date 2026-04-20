package com.my.docscannerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.my.docscannerapp.screens.home.HomeScreen
import com.my.docscannerapp.ui.theme.DocScannerAppTheme
import com.my.docscannerapp.viewmodels.pdfViewModel

class MainActivity : ComponentActivity() {
    private val pdfViewModel by viewModels<pdfViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition {pdfViewModel.isSplashScreen}

        enableEdgeToEdge()
        setContent {
            DocScannerAppTheme {
                    HomeScreen(pdfViewModel)
            }
        }
    }
}
