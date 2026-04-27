package com.my.docscannerapp.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.docscannerapp.data.models.pdfEntity
import com.my.docscannerapp.data.repository.pdfRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class PdfViewModel(application: Application): ViewModel() {
    var isSplashScreen by mutableStateOf(false)
    var showRenameDialog by mutableStateOf(false)
    var LoadingDialog by mutableStateOf(false)

    private val pdfRepository = pdfRepository(application)

    private val _pdfStateFlow = MutableStateFlow<List<pdfEntity>>(arrayListOf())
    val pdfStateFlow: StateFlow<List<pdfEntity>>
        get() = _pdfStateFlow

    var currentPdfEntity: pdfEntity? by mutableStateOf(null)

    init {
        viewModelScope.launch {
            delay(3000)
            isSplashScreen=false
        }

        viewModelScope.launch(Dispatchers.IO){
            pdfRepository.getPdfList().catch{
                it.printStackTrace()
            }.collect{
                _pdfStateFlow.emit(it)
            }
        }


    }
     fun insertPdf(pdfEntity: pdfEntity){
         viewModelScope.launch(Dispatchers.IO) {
             val result = pdfRepository.insertPdf(pdfEntity)

         }
    }
    fun deletePdf(pdfEntity: pdfEntity){
        viewModelScope.launch(Dispatchers.IO) {
            val result = pdfRepository.deletePdf(pdfEntity)

        }
    }
    fun updatePdf(pdfEntity: pdfEntity){
        viewModelScope.launch(Dispatchers.IO) {
            val result = pdfRepository.updatePdf(pdfEntity)

        }
    }
}