package com.my.docscannerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.my.docscannerapp.screens.HomeScreen
import com.my.docscannerapp.ui.theme.DocScannerAppTheme
import com.my.docscannerapp.viewmodels.pdfViewModel

class MainActivity : ComponentActivity() {
    private val pdfViewModel by viewModels<pdfViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DocScannerAppTheme {

                splashScreen.setKeepOnScreenCondition {pdfViewModel.isSplashScreen}

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen()
                }
            }
        }
    }
}
