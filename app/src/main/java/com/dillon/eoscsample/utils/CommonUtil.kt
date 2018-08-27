package com.dillon.eoscsample.utils

import com.blankj.utilcode.util.ToastUtils
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.dillon.eoscsample.R

object CommonUtil {
    fun startWithLetter(str: String): Boolean {
        val chars = CharArray(1)
        chars[0] = str[0]
        if (chars[0] >= 'a' && chars[0] <= 'z') {
            return true
        }
        return false
    }
}