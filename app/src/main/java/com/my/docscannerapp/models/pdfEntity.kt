package com.my.docscannerapp.models

import java.util.Date


//pdf UI
data class pdfEntity(
    val id: String,
    val name: String,
    val size: String,
    val lastModifiedDate: Date
)
