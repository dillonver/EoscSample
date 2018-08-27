package com.dillon.eoscsample.modules.eosforce.transfer

import com.dillon.eoscsample.base.IBasePresenter
import com.dillon.eoscsample.base.IBaseView

/**
 * Created by dillon on 2018/3/17.
 */
interface EosTransferContract {

    interface View : IBaseView {

        //转账新建失败
        fun transferFailure(errorMsg: String)

        //转账新建成功
        fun transferSuccess(successMsg: String)

        //转账新建执行状态
        fun transferStutas(statusMsg: String)

    }


    interface Presenter : IBasePresenter<View> {

        //新建EOS原力账号
        fun transferEos(message: String)
    }
}