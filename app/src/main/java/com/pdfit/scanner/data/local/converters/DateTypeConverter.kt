package com.pdfit.scanner.data.local.converters

import androidx.room.TypeConverter
import java.util.Date

class DateTypeConverter {

    @TypeConverter
    fun FromTimestamp(value: Long): Date
    {
        return Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date : Date):Long{
        return date.time
    }
}