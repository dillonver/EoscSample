package com.dillon.eoscsample.modules.welcome

import android.content.Intent
import android.os.Handler
import android.os.Message
import android.view.View
import com.dillon.eoscsample.R
import com.dillon.eoscsample.base.BaseActivity
import com.blankj.utilcode.util.ActivityUtils
import com.dillon.eoscsample.modules.eosforce.create.EosCreateActivity
import org.jetbrains.anko.toast

class WelcomeActivity : BaseActivity(), View.OnClickListener {


    private val TIME = 500
    private val GO_HOME = 100
    private val GO_GUIDE = 200
    private val GO_LOGIN = 300
    private val GO_CREATWALLET = 400
    private var isFirstIn = false
    private val mHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {

                GO_CREATWALLET -> goCreateWallet()
            }
        }
    }

    override fun layoutId(): Int {
        return R.layout.activity_welcome
    }

    override fun onClick(v: View?) {
    }

    override fun initData() {
        super.initData()

        mHandler.sendEmptyMessageDelayed(GO_CREATWALLET, TIME.toLong())
    }



    private fun goCreateWallet() {
        //进入创建钱包页
        startActivity(Intent(this, EosCreateActivity::class.java))
        finish()
    }

    override fun netConnectFail() {
        super.netConnectFail()
        if (ActivityUtils.getTopActivity() == this) {
            toast("网络连接恢复")
        }
    }

    override fun netReconnect(netWorkStates: Int) {
        super.netReconnect(netWorkStates)
        if (ActivityUtils.getTopActivity() == this) {
            toast("网络连接失败")
        }
    }

    override fun onBackPressedSupport() {
        super.onBackPressedSupport()
        logOutApp()
    }
}
