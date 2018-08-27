package com.dillon.eoscsample.modules.eosforce.create

import android.content.Intent
import android.view.View
import com.dillon.eoscsample.R
import com.dillon.eoscsample.base.BaseActivity
import com.dillon.eoscsample.blockchain.cypto.ec.EosPrivateKey
import com.dillon.eoscsample.modules.eosforce.transfer.EosTransferActivity
import com.dillon.eoscsample.utils.CommonUtil
import com.dillon.eoscsample.utils.PublicAndPrivateKeyUtils
import kotlinx.android.synthetic.main.activity_eos_create.*
import org.jetbrains.anko.toast


class EosCreateActivity : BaseActivity(), EosCreateContract.View, View.OnClickListener {


    private var mOwnerKey: EosPrivateKey? = null
    private var privateKeyStr = ""
    private var publicKeyStr = ""
    private val mPresenter by lazy {
        EosCreatePre(this)
    }

    override fun layoutId(): Int {
        return R.layout.activity_eos_create
    }

    override fun initListener() {
        super.initListener()
        tv_create_account.setOnClickListener(this)
        tv_go_transfer.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            tv_create_account -> {
                mOwnerKey = PublicAndPrivateKeyUtils.getPrivateKey(1)[0]
                privateKeyStr = mOwnerKey!!.toString()
                publicKeyStr = mOwnerKey!!.publicKey.toString()
                tv_private_key.text = privateKeyStr
                tv_public_key.text = publicKeyStr
                var userName = tv_user_name.text.toString().trim()
                if (userName.isNotEmpty() && publicKeyStr.isNotEmpty() && CommonUtil.startWithLetter(userName)) {
                    tv_create_account.isClickable = false
                    mPresenter.createEos(publicKeyStr, userName)
                } else {
                    toast("参数错误")
                }

            }
            tv_go_transfer -> {
                startActivity(Intent(this, EosTransferActivity::class.java))
            }

        }
    }

    override fun createFailure(errorMsg: String) {
        toast(errorMsg)
        tv_create_account.isClickable = true
        tv_create_account.text = "点击创建账号"
    }

    override fun createSuccess(successMsg: String) {
        toast(successMsg)
        tv_create_account.isClickable = true
        tv_create_account.text = "点击创建账号"
    }

    override fun createStutas(statusMsg: String) {
        tv_create_account.text = statusMsg
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }


}
