package com.dillon.eoscsample.modules.eosforce.transfer

import com.dillon.eoscsample.base.BasePresenter
import com.dillon.eoscsample.modules.eosforce.transfer.EosTransferContract
import com.dillon.eoscsample.modules.eosforce.transfer.EosTransferModel

class EosTransferPre(private var view: EosTransferContract.View) : BasePresenter<EosTransferContract.View>(), EosTransferContract.Presenter, EosTransferModel.OnFinishedListener {

    private val transferModel by lazy {
        EosTransferModel()
    }

    override fun transferEos(message: String) {
        transferModel.getJsonToBin(message, this)
    }


    override fun onSuccess(successMsg: String) {
        view.transferSuccess(successMsg)
    }

    override fun onFailure(errorMsg: String) {
        view.transferFailure(errorMsg)

    }

    override fun onStatus(statusMsg: String) {
        view.transferStutas(statusMsg)

    }


}
