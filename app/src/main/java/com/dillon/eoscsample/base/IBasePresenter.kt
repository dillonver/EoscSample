package com.dillon.eoscsample.base

interface IBasePresenter<in V : IBaseView> {
    fun attachView(mRootView: V)

    fun detachView()
}