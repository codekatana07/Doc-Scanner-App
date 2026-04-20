package com.my.docscannerapp.screens.common

import android.icu.text.CurrencyPluralInfo
import android.os.Message
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.my.docscannerapp.viewmodels.pdfViewModel

@Composable
fun LoadingDialog(pdfViewModel: pdfViewModel) {
    if(pdfViewModel.LoadingDialog){
        Dialog(onDismissRequest = {
            pdfViewModel.LoadingDialog = false
        }) {
            Box(
                modifier = Modifier
                    .size(100.dp),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }
    }
}