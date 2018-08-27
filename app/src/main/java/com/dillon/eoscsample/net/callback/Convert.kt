package com.dillon.eoscsample.net.callback

import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import com.google.gson.stream.JsonReader
import com.readystatesoftware.chuck.internal.support.JsonConvertor
import java.io.Reader
import java.lang.reflect.Type

object Convert {

    private fun create(): Gson {
        return GsonHolder.gson
    }

    private object GsonHolder {
          val gson = Gson()
    }

    @Throws(JsonIOException::class, JsonSyntaxException::class)
    fun <T> fromJson(json: String, type: Class<T>): T {
        return create().fromJson(json, type)
    }

    fun <T> fromJson(json: String, type: Type): T {
        return create().fromJson(json, type)
    }

    @Throws(JsonIOException::class, JsonSyntaxException::class)
    fun <T> fromJson(reader: JsonReader, typeOfT: Type): T {
        return create().fromJson(reader, typeOfT)
    }

    @Throws(JsonSyntaxException::class, JsonIOException::class)
    fun <T> fromJson(json: Reader, classOfT: Class<T>): T {
        return create().fromJson(json, classOfT)
    }

    @Throws(JsonIOException::class, JsonSyntaxException::class)
    fun <T> fromJson(json: Reader, typeOfT: Type): T {
        return create().fromJson(json, typeOfT)
    }

    fun toJson(src: Any): String {
        return create().toJson(src)
    }

    fun toJson(src: Any, typeOfSrc: Type): String {
        return create().toJson(src, typeOfSrc)
    }

    fun formatJson(json: String): String {
        try {
            val jp = JsonParser()
            val je = jp.parse(json)
            return JsonConvertor.getInstance().toJson(je)
        } catch (e: Exception) {
            return json
        }

    }

    fun formatJson(src: Any): String {
        try {
            val jp = JsonParser()
            val je = jp.parse(toJson(src))
            return JsonConvertor.getInstance().toJson(je)
        } catch (e: Exception) {
            return e.message.toString()
        }

    }
}
