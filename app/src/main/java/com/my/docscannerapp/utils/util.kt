package com.my.docscannerapp.utils

import android.content.Context
import android.os.Message
import android.widget.Toast

fun Context.showToast(message: String){
    Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
}

