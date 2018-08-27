package com.dillon.eoscsample.modules.eosforce.transfer

import cc.byyourside.niceface.api.BaseUrl
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.ToastUtils
import com.dillon.eoscsample.blockchain.bean.*
import com.dillon.eoscsample.blockchain.bean.responsehistransaction.ResponseHistoryTransaction
import com.dillon.eoscsample.blockchain.cypto.ec.EosPrivateKey
import com.dillon.eoscsample.blockchain.types.TypeChainId
import com.dillon.eoscsample.blockchain.util.GsonEosTypeAdapterFactory
import com.dillon.eoscsample.net.HttpUtils
import com.dillon.eoscsample.net.callback.JsonCallback
import com.dillon.eoscsample.usual.Constants
import com.dillon.eoscsample.usual.ParamsKey
import com.dillon.eoscsample.utils.GsonUtil
import com.google.gson.GsonBuilder
import com.lzy.okgo.model.Response
import java.util.ArrayList
import java.util.HashMap

class EosTransferModel {
    interface OnFinishedListener {

        fun onSuccess(success: String)

        fun onFailure(error: String)

        fun onStatus(status: String)

    }

    var versionCode = AppUtils.getAppVersionCode()
    //本地签名，测试用。
    private var creator = "dilloneos"
    private var creatorPriKey = "5Jc6CdQ4YGfAFi6KRyNVpuwvDr9JqSEhSyjDxSqtzizktDRFC6u"
    private var message = ""
    private var mGson = GsonBuilder()
            .registerTypeAdapterFactory(GsonEosTypeAdapterFactory())
            .excludeFieldsWithoutExposeAnnotation().create()

    //一、序列化转账交易的 json
     fun getJsonToBin(message:String, listener: OnFinishedListener) {
        val jsonToBinRequest = JsonToBinTransfer(Constants.EOSCONTRACT_EOSIO_TOKEN, Constants.ACTION_TRANSFER, message.replace("\\r|\\n".toRegex(), ""))
        HttpUtils.postRequest(BaseUrl.HTTP_get_abi_json_to_bin, this, mGson.toJson(jsonToBinRequest), object : JsonCallback<ResponseJsonToBin>() {
            override fun onSuccess(response: Response<ResponseJsonToBin>) {
                if (response.body() != null) {
                    getChainInfo(response.body(),listener)
                    listener.onStatus("序列化数据成功")
                } else {
                    listener.onFailure("序列化数据失败")
                }
            }

            override fun onError(response: Response<ResponseJsonToBin>?) {
                listener.onFailure("系统错误")

            }
        })
    }

    //二、获取 EOS 区块链的最新区块号
    private fun getChainInfo(responseJsonToBin: ResponseJsonToBin, listener: OnFinishedListener) {
        HttpUtils.postRequest(BaseUrl.HTTP_get_chain_info, this, HashMap(), object : JsonCallback<ChainInfo>() {
            override fun onSuccess(response: Response<ChainInfo>) {
                if (response.body() != null) {
                    getChainBlock(responseJsonToBin, response.body(), listener)
                    listener.onStatus("获取最新区块号成功")

                } else {
                    listener.onFailure("获取最新区块号失败")
                }
            }

            override fun onError(response: Response<ChainInfo>?) {
                listener.onFailure("系统错误")
            }
        })
    }

    //三、获取最新区块的具体信息
    private fun getChainBlock(responseJsonToBin: ResponseJsonToBin, chainInfo: ChainInfo, listener: OnFinishedListener) {
        val map = HashMap<String, String>()
        map[ParamsKey.blockNumOrId] = chainInfo.head_block_num!!
        HttpUtils.postRequest(BaseUrl.HTTP_get_chain_block, this, GsonUtil.gsonString(map)!!, object : JsonCallback<ChainBlock>() {
            override fun onSuccess(response: Response<ChainBlock>) {
                if (response.body() != null) {
                    getRequiredFee(chainInfo, createTransaction(responseJsonToBin.binargs!!, arrayOf(creator + "@" + Constants.PERMISSION), response.body()), listener)
                    listener.onStatus("获取最新区块信息成功")

                } else {

                    listener.onFailure("最新区块的信息失败")

                }
            }

            override fun onError(response: Response<ChainBlock>?) {
                listener.onFailure("系统错误")

            }
        })
    }

    //四创建一个trasaction
    private fun createTransaction(dataAsHex: String,
                                  permissions: Array<String>, chainBlock: ChainBlock?): SignedTransaction {

        val action = Action(Constants.EOSCONTRACT_EOSIO, Constants.ACTION_TRANSFER)
        action.setAuthorization(permissions)
        action.setData(dataAsHex)


        val txn = SignedTransaction()
        txn.addAction(action)
        txn.putSignatures(ArrayList())


        if (null != chainBlock) {
            txn.setReferenceBlock(chainBlock.block_num, chainBlock.ref_block_prefix)
            txn.expiration = chainBlock.getTimeAfterHeadBlockTime(3000000)
        }
        return txn
    }

    //五获取交易费用
    private fun getRequiredFee(chainInfo: ChainInfo, signedTransaction: SignedTransaction, listener: OnFinishedListener) {
        HttpUtils.postRequest(BaseUrl.HTTP_get_required_fee, this, mGson.toJson(GetRequiredFee(signedTransaction)), object : JsonCallback<ResponseRequiredFee>() {
            override fun onSuccess(response: Response<ResponseRequiredFee>) {
                if (response.body() != null) {
                    val requireFee = response.body()
                    signedTransaction.fee = requireFee.required_fee
                    signAndPushTransaction(chainInfo, signedTransaction,listener)
                    listener.onStatus("获取交易费用成功")
                } else {
                    listener.onFailure("获取交易费用失败")
                }
            }

            override fun onError(response: Response<ResponseRequiredFee>?) {
                listener.onFailure("系统错误")

            }
        })
    }

    //六签名并推送交易
    private fun signAndPushTransaction(chainInfo: ChainInfo, signedTransaction: SignedTransaction, listener: OnFinishedListener) {
        signedTransaction.sign(EosPrivateKey(creatorPriKey), TypeChainId(chainInfo.chain_id))
        val packedTransactionCreateEos = PackedTransactionCreateEos(signedTransaction)
        HttpUtils.postRequest(BaseUrl.HTTP_push_transaction, this, mGson.toJson(packedTransactionCreateEos), object : JsonCallback<ResponsePushTransaction>() {
            override fun onSuccess(response: Response<ResponsePushTransaction>) {
                if (response.body() != null) {
                    listener.onSuccess("转账交易成功")

                    //交易会延迟，这里提交成功，当作交易成功
                    //getHistoryTransaction(response.body(),listener)

                } else {
                    listener.onFailure("转账交易失败")

                }
            }

            override fun onError(response: Response<ResponsePushTransaction>?) {
                super.onError(response)
                listener.onFailure("系统错误")

            }

        })
    }

    private fun getHistoryTransaction(pushRes: ResponsePushTransaction, listener: OnFinishedListener) {
        val map = HashMap<String, String>()
        map[ParamsKey.trxId] = pushRes.transaction_id!!
        HttpUtils.postRequest(BaseUrl.HTTP_push_transaction, this, GsonUtil.gsonString(map)!!, object : JsonCallback<ResponseHistoryTransaction>() {
            override fun onSuccess(response: Response<ResponseHistoryTransaction>) {
                if (response.body() != null) {
                    val hisTransaction = response.body()
                    if (hisTransaction.block_num <= hisTransaction.last_irreversible_block) {
                        listener.onSuccess("转账成功!")
                    }
                }
            }

        })
    }

}