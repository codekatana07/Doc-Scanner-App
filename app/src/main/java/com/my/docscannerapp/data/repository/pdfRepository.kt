package com.my.docscannerapp.data.repository

import android.app.Application
import com.my.docscannerapp.data.local.pdfDatabase
import com.my.docscannerapp.data.models.pdfEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

//business login in repo
class pdfRepository(application: Application) {
    private var pdfDao = pdfDatabase.getInstance(application).pdfDao

    fun getPdfList() = pdfDao.getAllPdfs().flowOn(Dispatchers.IO)

    suspend fun insertPdf(pdfEntity: pdfEntity) : Long{
        return pdfDao.insertPdf(pdfEntity)
    }

    suspend fun deletePdf(pdfEntity: pdfEntity) : Int{
        return pdfDao.deletePdf(pdfEntity)
    }

    suspend fun updatePdf(pdfEntity: pdfEntity) : Int{
        return pdfDao.updatePdf(pdfEntity)
    }

}