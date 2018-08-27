package com.dillon.eoscsample.modules.eosforce.transfer

import android.view.View
import com.dillon.eoscsample.R
import com.dillon.eoscsample.base.BaseActivity
import com.dillon.eoscsample.blockchain.bean.TransferEosMessageBean
import com.dillon.eoscsample.utils.CommonUtil
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_eos_transfer.*
import org.jetbrains.anko.toast


class EosTransferActivity : BaseActivity(), EosTransferContract.View, View.OnClickListener {


    private val mPresenter by lazy {
        EosTransferPre(this)
    }

    override fun layoutId(): Int {
        return R.layout.activity_eos_transfer
    }


    override fun initListener() {
        super.initListener()
        tv_eos_transfer.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            tv_eos_transfer -> {

                //测试dilloneos转账victor 0.0100 EOS
                var toWho = ed_to_who.text.toString().trim()
                if (toWho.isNotEmpty()&& CommonUtil.startWithLetter(toWho)) {
                    var message = Gson().toJson(TransferEosMessageBean("测试转账", toWho,
                            "0.1000 EOS",
                            "dilloneos"))

                    mPresenter.transferEos(message)
                    tv_eos_transfer.isClickable=false
                } else {
                    toast("参数错误")
                }

            }

        }


    }

    override fun transferFailure(errorMsg: String) {
        toast(errorMsg)
        tv_eos_transfer.isClickable = true
        tv_eos_transfer.text = "立即转账"
    }

    override fun transferSuccess(successMsg: String) {
        toast(successMsg)
        tv_eos_transfer.isClickable = true
        tv_eos_transfer.text = "立即转账"
    }

    override fun transferStutas(statusMsg: String) {
        tv_eos_transfer.text = statusMsg
    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()

    }
}
