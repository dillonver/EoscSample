package cc.byyourside.niceface.api

/**
 * Created by dillon on 2017/6/29.
 */

object BaseUrl {
    private val HOST = " "
    val userRegisterApi = HOST + ""
    val userLoginApi = HOST + ""
    val HTTP_CHAIN_ADDRESS = "https://w3.eosforce.cn/v1/chain/"
    val HTTP_CHAIN_ADDRESS_FOR_JSON_TO_BIN = "https://api.eosnewyork.io/v1/chain/"

    // 获取区块链状态
    val HTTP_get_chain_info = HTTP_CHAIN_ADDRESS + "get_info"
    // 获取区块链
    val HTTP_get_chain_block = HTTP_CHAIN_ADDRESS + "get_block"
    // 交易JSON序列化
    val HTTP_get_abi_json_to_bin = HTTP_CHAIN_ADDRESS_FOR_JSON_TO_BIN + "abi_json_to_bin"
    // 获取交易手续费
    val HTTP_get_required_fee = HTTP_CHAIN_ADDRESS + "get_required_fee"
    // 发起交易
    val HTTP_push_transaction = HTTP_CHAIN_ADDRESS + "push_transaction"
    // 历史交易
    val HTTP_get_history_transaction = " https://w3.eosforce.cn/v1/history/get_transaction"
}
