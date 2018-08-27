package com.dillon.eoscsample.utils

import android.util.Log

/**
 * Created by dillon on 2016/5/26.
 */
object LogUtil {
    private val LOG = true
    private val LOGV = true
    private val LOGD = true
    private val LOGI = true
    private val LOGW = true
    private val LOGE = true

    fun v(tag: Any, mess: Any) {
        if (LOGV && LOG) {
            Log.v(tag.toString(), mess.toString())
        }
    }

    fun v(tag: Any, mess: Any, ee: Exception) {
        if (LOGW && LOG) {
            Log.v(tag.toString(), mess.toString(), ee)
        }
    }

    fun d(tag: Any, mess: Any) {
        if (LOGD && LOG) {
            Log.d(tag.toString(), mess.toString())
        }
    }

    fun d(tag: Any, mess: Any, ee: Exception) {
        if (LOGW && LOG) {
            Log.d(tag.toString(), mess.toString(), ee)
        }
    }

    fun i(tag: Any, mess: Any) {
        if (LOGI && LOG) {
            Log.i(tag.toString(), mess.toString())
        }
    }

    fun i(tag: Any, mess: Any, ee: Exception) {
        if (LOGW && LOG) {
            Log.i(tag.toString(), mess.toString(), ee)
        }
    }

    fun w(tag: Any, mess: Any) {
        if (LOGW && LOG) {
            Log.w(tag.toString(), mess.toString())
        }
    }

    fun w(tag: Any, mess: Any, ee: Exception) {
        if (LOGW && LOG) {
            Log.w(tag.toString(), mess.toString(), ee)
        }
    }

    fun e(tag: Any, mess: Any) {
        if (LOGE && LOG) {
            Log.e(tag.toString(), mess.toString())
        }
    }

    fun e(tag: Any, mess: Any, ee: Exception) {
        if (LOGW && LOG) {
            Log.e(tag.toString(), mess.toString(), ee)
        }
    }
}
