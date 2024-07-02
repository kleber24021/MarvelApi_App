package com.drewsoft.marvelapiapp.core.utils

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeAdapter : TypeAdapter<LocalDateTime>(){
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    override fun write(out: JsonWriter?, value: LocalDateTime?) {
        if (value == null){
            out?.nullValue()
        }else{
            out?.value(value.format(formatter))
        }
    }

    override fun read(`in`: JsonReader?): LocalDateTime? {
        return if (`in`?.peek() == JsonToken. NULL){
            `in`.nextNull()
            null
        }else{
            LocalDateTime.parse(`in`?.nextString(), formatter)
        }
    }

}