package com.hva.madlevel4task2.helper

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*
import kotlin.collections.ArrayList

class Converters {

    companion object {
        @TypeConverter
        @JvmStatic
        fun fromTimeStamp(value: Long?): Date? {
            return value?.let { Date(it) }
        }

        @TypeConverter
        @JvmStatic
        fun dateToTimeStamp(date: Date?): Long? {
            return date?.time?.toLong()
        }

        @TypeConverter
        @JvmStatic
        fun detailsToString(value: ArrayList<String>):String {
            val gson = Gson()
            val type = object : TypeToken<ArrayList<String>>() {}.type
            return gson.toJson(value, type)
        }

        @TypeConverter
        @JvmStatic
        fun stringToArray(value: String) : ArrayList<String> {
            val gson = Gson()
            val type = object : TypeToken<ArrayList<String>>() {}.type
            return gson.fromJson(value, type)
        }

    }
}