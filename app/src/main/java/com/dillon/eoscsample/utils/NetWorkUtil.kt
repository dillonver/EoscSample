package com.dillon.eoscsample.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by dillon on 2018/3/18.
 */
object NetWorkUtil {

    // 没有连接网络
    val NETWORK_NONE = -1

    //移动网络
    val NETWORK_MOBILE = 0

    //无线网络
    val NETWORK_WIFI = 1

    fun getNetWorkState(context: Context): Int {

        // 得到连接管理器对象
        val connectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetworkInfo = connectivityManager
                .activeNetworkInfo
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
            if (activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI) {
                return NETWORK_WIFI
            } else if (activeNetworkInfo.type == ConnectivityManager.TYPE_MOBILE) {
                return NETWORK_MOBILE
            }
        } else {
            return NETWORK_NONE
        }
        return NETWORK_NONE
    }

    //判断有无网络  true 有网, false 没有网络.
    fun netConnectStatus(netWorkState: Int): Boolean {
        if (netWorkState == NetWorkUtil.NETWORK_WIFI || netWorkState == NetWorkUtil.NETWORK_MOBILE) {
            return true
        } else if (netWorkState == NetWorkUtil.NETWORK_NONE) {
            return false
        }
        return false
    }
}