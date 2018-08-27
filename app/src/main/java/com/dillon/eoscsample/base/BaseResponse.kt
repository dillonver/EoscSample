package cc.byyourside.niceface.api

/**
 * Created by dillon on 2017/6/16.
 */

open class BaseResponse {
    var code: Int = 0//状态码
    var msg: String = ""//信息
    var error_code: Int = 0 //错误码
    var error_msg: String = "" //错误信息

}
