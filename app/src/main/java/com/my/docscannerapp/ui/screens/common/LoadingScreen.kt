package com.my.docscannerapp.ui.screens.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.my.docscannerapp.ui.viewmodels.PdfViewModel

@Composable
fun LoadingDialog(pdfViewModel: PdfViewModel) {
    if(pdfViewModel.LoadingDialog){
        Dialog(onDismissRequest = {
            pdfViewModel.LoadingDialog = false
        }) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(MaterialTheme.colorScheme.surface),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }
    }
}