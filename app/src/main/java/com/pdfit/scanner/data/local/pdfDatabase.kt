package com.pdfit.scanner.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pdfit.scanner.data.local.converters.DateTypeConverter
import com.pdfit.scanner.data.local.dao.pdfDao
import com.pdfit.scanner.data.models.pdfEntity

@Database(
    entities = [pdfEntity::class], version = 1, exportSchema = false
)
@TypeConverters(DateTypeConverter::class)
abstract class pdfDatabase : RoomDatabase(){

    abstract val pdfDao: pdfDao
    companion object{

        @Volatile
        private var INSTANCES: pdfDatabase? = null

        //create instance only one time
        fun getInstance(context: Context) : pdfDatabase {
            synchronized(this){
                return INSTANCES?: Room.databaseBuilder(
                    context.applicationContext,
                    pdfDatabase::class.java,
                    "pdf_db"
                ).build().also {
                    INSTANCES = it
                }
            }
        }
    }
}