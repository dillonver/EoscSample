package com.dillon.eoscsample.base

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.yokeyword.fragmentation.SupportFragment


abstract class BaseFragment :TakePhotoFragment() {
    //双击fragment标题返回顶部
    private val waitTime = 1500L
    private var touchTime: Long = 0
    var barShowStatus = true //bottom正在显示，true：正在显示，false:正在隐藏
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        initData()
    }

    abstract fun getLayoutId(): Int

    open fun initView() {}

    open fun initListener() {}

    open fun initData() {}


    //设置回到顶部
    fun smoothTop(recyclerView: RecyclerView) {
        if (System.currentTimeMillis() - touchTime < waitTime) {
            recyclerView.smoothScrollToPosition(0)
            recyclerView.scrollTo(0, 0)
        } else {
            touchTime = System.currentTimeMillis()
        }
    }


}
