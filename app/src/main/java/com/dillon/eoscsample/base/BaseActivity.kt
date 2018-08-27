package com.dillon.eoscsample.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import com.dillon.eoscsample.R
import com.dillon.eoscsample.utils.NetWorkUtil
import com.dillon.eoscsample.utils.NetWorkUtil.netConnectStatus
import com.dillon.eoscsample.utils.StatusBarUtil
import com.blankj.utilcode.util.AppUtils
import me.yokeyword.fragmentation.SupportActivity
import org.jetbrains.anko.toast

abstract class BaseActivity : TakePhotoActivity() {

    // 再点一次退出程序时间设置
    private var waitTime = 2000L
    private var touchTime: Long = 0
    private var netReceiver: NetBroadcastReceiver? = null
    private var netConnectStatusOld = true//网络的初始连接状态 true为连接

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseBeforeSetContentView()
        beforeSetContentView()
        setContentView(layoutId())
        baseInitData()
        initData()
        initView()
        initListener()

    }

    open fun beforeSetContentView() {}

    abstract fun layoutId(): Int

    open fun initData() {}

    open fun initView() {}

    open fun initListener() {}

    open fun getContext() = this

    private fun baseBeforeSetContentView() {
        StatusBarUtil.setBarWhiteBackGroundBlackText(this)
    }

    private fun baseInitData() {
        netConnectStatusOld = netConnectStatus(NetWorkUtil.getNetWorkState(this))
        registerReceiver()
    }

    private fun registerReceiver() {
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        netReceiver = NetBroadcastReceiver()
        registerReceiver(netReceiver, filter)
    }

    private fun unRegisterReceiver() {
        if (netReceiver != null) {
            unregisterReceiver(netReceiver)
        }
    }

    fun logOutApp() {
        if (System.currentTimeMillis() - touchTime < waitTime) {
            AppUtils.exitApp()
        } else {
            touchTime = System.currentTimeMillis()
            toast(resources.getString(R.string.double_click_log_out))
        }
    }

    //监听网络状态
    inner class NetBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent!!.action == (ConnectivityManager.CONNECTIVITY_ACTION)) {
                // 接口回调传过去状态的类型
                val netWorkState = NetWorkUtil.getNetWorkState(context!!)
                netChange(netWorkState)
                netConnectStatusOld = netConnectStatus(netWorkState)

            }
        }
    }

    //网络状态改变
    private fun netChange(netWorkStates: Int) {
        if (NetWorkUtil.netConnectStatus(netWorkStates)) {
            if (!netConnectStatusOld) {
                netReconnect(netWorkStates)
            }
        } else {
            netConnectFail()
        }
    }

    open fun netReconnect(netWorkStates: Int) {
       // toast("网络恢复连接")

    }

    open fun netConnectFail() {
      //  toast("网络不可用")

    }

    override fun onDestroy() {
        unRegisterReceiver()
        super.onDestroy()
    }

}
