package com.dillon.eoscsample.modules.eosforce.create

import com.dillon.eoscsample.base.IBasePresenter
import com.dillon.eoscsample.base.IBaseView
/**
 * Created by dillon on 2018/3/17.
 */
interface EosCreateContract {

    interface View : IBaseView {

        //账号新建失败
        fun createFailure(errorMsg: String)

        //账号新建成功
        fun createSuccess(successMsg: String)

        //账号新建执行状态
        fun createStutas(statusMsg: String)

    }


    interface Presenter : IBasePresenter<View> {

        //新建EOS原力账号
        fun createEos(userPublicKey: String, userName: String)
    }
}