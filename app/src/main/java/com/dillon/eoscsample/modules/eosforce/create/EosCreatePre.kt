package com.dillon.eoscsample.modules.eosforce.create

import com.dillon.eoscsample.base.BasePresenter
import com.dillon.eoscsample.modules.eosforce.transfer.EosTransferModel

class EosCreatePre(private var view: EosCreateContract.View) : BasePresenter<EosCreateContract.View>(), EosCreateContract.Presenter, EosCreateModel.OnFinishedListener {
    private val createModel by lazy {
        EosCreateModel()
    }

    override fun createEos(userPublicKey: String, userName: String) {
        createModel.getJsonToBin(userPublicKey, userName, this)
    }

    override fun onCreateSuccess(successMsg: String) {
        view.createSuccess(successMsg)
    }

    override fun onCreateFailure(errorMsg: String) {
        view.createFailure(errorMsg)

    }

    override fun onCreateStatus(statusMsg: String) {
        view.createStutas(statusMsg)

    }


}
