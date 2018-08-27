package com.dillon.eoscsample.utils

import com.blankj.utilcode.util.ToastUtils
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.dillon.eoscsample.R

object GsonUtil {
    private var gson: Gson? = null

    init {
        if (gson == null) {
            gson = Gson()
        }
    }

    /**
     * 对象转成json
     *
     * @param object
     * @return json
     */
    fun gsonString(`object`: Any): String? {
        var gsonString: String? = null
        if (gson != null) {
            gsonString = gson!!.toJson(`object`)
        }
        return gsonString
    }

    /**
     * Json转成对象
     *
     * @param gsonString
     * @param cls
     * @return 对象
     */
    fun <T> gsonToBean(gsonString: String, cls: Class<T>): T? {
        var t: T? = null
        if (gson != null) {
            t = gson!!.fromJson(gsonString, cls)
        }
        return t
    }

    /**
     * json转成list<T>
     *
     * @param gsonString
     * @param cls
     * @return list<T>
    </T></T> */
    fun <T> gsonToList(gsonString: String, cls: Class<T>): List<T>? {
        var list: List<T>? = null
        if (gson != null) {
            list = gson!!.fromJson<List<T>>(gsonString, object : TypeToken<List<T>>() {

            }.type)
        }
        return list
    }

    /**
     * json转成list中有map的
     *
     * @param gsonString
     * @return List<Map></Map> < String ,   T>>
     */
    fun <T> gsonToListMaps(gsonString: String): List<Map<String, T>>? {
        var list: List<Map<String, T>>? = null
        if (gson != null) {
            list = gson!!.fromJson<List<Map<String, T>>>(gsonString, object : TypeToken<List<Map<String, T>>>() {

            }.type)
        }
        return list
    }

    /**
     * json转成map的
     *
     * @param gsonString
     * @return Map<String , T>
    </String> */
    fun <T> gsonToMaps(gsonString: String): Map<String, T>? {
        var map: Map<String, T>? = null
        if (gson != null) {
            map = gson!!.fromJson<Map<String, T>>(gsonString, object : TypeToken<Map<String, T>>() {

            }.type)
        }
        return map
    }
}