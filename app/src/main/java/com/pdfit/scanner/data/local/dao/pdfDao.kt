package com.pdfit.scanner.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.pdfit.scanner.data.models.pdfEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface pdfDao {
    //suspend function make it run in background, because we do not want DB to run on main thread
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPdf(pdf: pdfEntity) : Long
    @Delete
    suspend fun deletePdf(pdf: pdfEntity) : Int
    @Update
    suspend fun updatePdf(pdf: pdfEntity) : Int
    //flow auto update database
    @Query("SELECT * FROM  pdfTable")
    fun getAllPdfs(): Flow<List<pdfEntity>>


}