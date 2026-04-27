package com.my.docscannerapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.my.docscannerapp.data.local.converters.DateTypeConverter
import com.my.docscannerapp.data.local.dao.pdfDao
import com.my.docscannerapp.data.models.pdfEntity
import kotlin.coroutines.CoroutineContext

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