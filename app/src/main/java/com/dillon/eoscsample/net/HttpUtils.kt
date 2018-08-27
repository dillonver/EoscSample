package com.dillon.eoscsample.net

import com.dillon.eoscsample.net.callback.JsonCallback
import com.lzy.okgo.OkGo
import com.lzy.okgo.model.HttpParams

/**
 * Created by dillon 2
 */
object HttpUtils {

    fun <T> getRequest(url: String, tag: Any, params: HttpParams, callback: JsonCallback<T>) {
        OkGo.get<T>(url)
                .tag(tag)
                .params(params)
                .execute(callback)
    }


    fun <T> postRequest(url: String, tag: Any, params: HttpParams, callback: JsonCallback<T>) {
        OkGo.post<T>(url)
                .tag(tag)
                .params(params)
                .execute(callback)
    }

    fun <T> getRequest(url: String, tag: Any, map: Map<String, String>, callback: JsonCallback<T>) {
        OkGo.get<T>(url)
                .tag(tag)
                .params(map)
                .execute(callback)
    }


    fun <T> postRequest(url: String, tag: Any, map: Map<String, String>, callback: JsonCallback<T>) {
        OkGo.post<T>(url)
                .tag(tag)
                .params(map)
                .execute(callback)
    }

    fun <T> postRequest(url: String, tag: Any, parms: String, callback: JsonCallback<T>) {
        OkGo.post<T>(url)
                .tag(tag)
                .upJson(parms)
                .execute(callback)
    }




}
