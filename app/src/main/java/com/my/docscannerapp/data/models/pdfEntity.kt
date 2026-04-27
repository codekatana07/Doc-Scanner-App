package com.my.docscannerapp.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


//pdf UI
@Entity(tableName = "pdfTable")
data class pdfEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "pdfId")
    val id: String,
    val name: String,
    val size: String,
    val lastModifiedDate: Date
)
