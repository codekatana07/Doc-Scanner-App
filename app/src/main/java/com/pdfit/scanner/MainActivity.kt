package com.pdfit.scanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.viewModelFactory
import com.pdfit.scanner.ui.screens.home.HomeScreen
import com.pdfit.scanner.ui.theme.DocScannerAppTheme
import com.pdfit.scanner.ui.viewmodels.PdfViewModel
import kotlin.getValue

class MainActivity : ComponentActivity() {
    private val pdfViewModel: PdfViewModel by viewModels{
        viewModelFactory {
            addInitializer(PdfViewModel::class){
                PdfViewModel(application)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition {pdfViewModel.isSplashScreen}

        enableEdgeToEdge()
        setContent {
            DocScannerAppTheme(pdfViewModel.isDarkMode,false) {
                    HomeScreen(pdfViewModel)
            }
        }
    }
}
