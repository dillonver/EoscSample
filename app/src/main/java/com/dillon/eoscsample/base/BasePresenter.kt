package com.dillon.eoscsample.base


open class BasePresenter<V : IBaseView> : IBasePresenter<V> {

    var mRootView: V? = null

    override fun attachView(mRootView: V) {
        this.mRootView = mRootView
    }

    override fun detachView() {
        mRootView = null
    }
}
