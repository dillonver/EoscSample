package com.dillon.eoscsample.utils

import android.Manifest
import com.tbruyelle.rxpermissions2.RxPermissions

/**
 * Created by dillon on 2018/3/19.
 */
object PermissionUtil {


    fun testPermission(rxPermissions: RxPermissions) {
        rxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe { granted ->
                    if (granted) {
                        //  tv_to_main.setOnClickListener(this)
                    } else {
                        // toast("弹窗解释权限、再次请求、finish")
                    }
                }
    }
}