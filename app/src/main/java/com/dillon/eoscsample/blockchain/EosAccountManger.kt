package com.dillon.eoscsample.blockchain

import android.content.Context

import com.blankj.utilcode.util.ToastUtils
import com.dillon.eoscsample.usual.Constants
import com.google.gson.GsonBuilder
import com.dillon.eoscsample.blockchain.bean.ChainBlock
import com.dillon.eoscsample.blockchain.bean.ChainInfo
import com.dillon.eoscsample.blockchain.bean.ResponseJsonToBin
import com.dillon.eoscsample.blockchain.bean.jsontobinforcreate.Active
import com.dillon.eoscsample.blockchain.bean.jsontobinforcreate.Args
import com.dillon.eoscsample.blockchain.bean.jsontobinforcreate.GetBinForCreateAccount
import com.dillon.eoscsample.blockchain.bean.jsontobinforcreate.Keys
import com.dillon.eoscsample.blockchain.bean.jsontobinforcreate.Owner
import com.dillon.eoscsample.blockchain.bean.GetRequiredFee
import com.dillon.eoscsample.blockchain.bean.ResponseRequiredFee
import com.dillon.eoscsample.blockchain.bean.ResponsePushTransaction
import com.dillon.eoscsample.blockchain.bean.Action
import com.dillon.eoscsample.blockchain.bean.PackedTransactionCreateEos
import com.dillon.eoscsample.blockchain.bean.SignedTransaction
import com.dillon.eoscsample.blockchain.cypto.ec.EosPrivateKey
import com.dillon.eoscsample.blockchain.types.TypeChainId
import com.dillon.eoscsample.blockchain.util.GsonEosTypeAdapterFactory
import com.dillon.eoscsample.net.HttpUtils
import com.dillon.eoscsample.net.callback.JsonCallback
import com.dillon.eoscsample.utils.GsonUtil
import com.lzy.okgo.model.Response

import java.util.ArrayList
import java.util.HashMap

import cc.byyourside.niceface.api.BaseUrl
import com.dillon.eoscsample.usual.ParamsKey


class EosAccountManger(internal var mContext: Context) {

    //测试用
    private var creator = "dilloneos"
    private var creatorPriKey = "5Jc6CdQ4YGfAFi6KRyNVpuwvDr9JqSEhSyjDxSqtzizktDRFC6u"

    private var newUserName = ""
    private var newUserKey = ""

    private var chainInfo = ChainInfo()
    private var chainBlock = ChainBlock()
    private var mResponseJsonToBin = ResponseJsonToBin()
    private var permissions: Array<String> = arrayOf()
    private var mGson = GsonBuilder()
            .registerTypeAdapterFactory(GsonEosTypeAdapterFactory())
            .excludeFieldsWithoutExposeAnnotation().create()

    fun pushAction(newUserName: String, newUserKey: String) {
        this.newUserKey = newUserKey
        this.newUserName = newUserName
        this.permissions = arrayOf(creator + "@" + Constants.PERMISSION)

        getJsonToBin()
    }

    //一、序列化新建账号的 json
    private fun getJsonToBin() {
        val getBinForCreateAccount = GetBinForCreateAccount()
        val keys = Keys()
        keys.key = newUserKey
        val keys1 = ArrayList<Keys>()
        keys1.add(keys)
        val owner = Owner()
        owner.keys = keys1
        val active = Active()
        active.keys = keys1
        val args = Args().setCreator(creator).setName(newUserName)
                .setOwner(owner).setActive(active)
        getBinForCreateAccount.args = args
        HttpUtils.postRequest(BaseUrl.HTTP_get_abi_json_to_bin, this, GsonUtil.gsonString(getBinForCreateAccount)!!, object : JsonCallback<ResponseJsonToBin>() {
            override fun onSuccess(response: Response<ResponseJsonToBin>) {
                if (response.body() != null) {
                    mResponseJsonToBin = response.body()
                    getChainInfo()
                } else {
                    ToastUtils.showLong(response.body().toString())
                }
            }
        })
    }

    //二、获取 EOS 区块链的最新区块号
    private fun getChainInfo() {
        HttpUtils.postRequest(BaseUrl.HTTP_get_chain_info, this, HashMap(), object : JsonCallback<ChainInfo>() {
            override fun onSuccess(response: Response<ChainInfo>) {
                if (response.body() != null) {
                    chainInfo = response.body()
                    getChainBlock(chainInfo.head_block_num + "")
                } else {
                    ToastUtils.showLong(response.body().toString())
                }
            }

        })
    }

    //三、获取最新区块的具体信息
    private fun getChainBlock(block_num_or_id: String) {
        val map = HashMap<String, String>()
        map[ParamsKey.blockNumOrId] = block_num_or_id
        HttpUtils.postRequest(BaseUrl.HTTP_get_chain_block, this, GsonUtil.gsonString(map)!!, object : JsonCallback<ChainBlock>() {
            override fun onSuccess(response: Response<ChainBlock>) {
                if (response.body() != null) {
                    chainBlock = response.body()
                    getRequiredFee(createTransaction(mResponseJsonToBin.binargs!!, permissions, chainBlock))
                } else {

                    ToastUtils.showLong(response.body().toString())
                }
            }
        })
    }

    //四创建一个trasaction
    private fun createTransaction(dataAsHex: String,
                                  permissions: Array<String>, chainBlock: ChainBlock?): SignedTransaction {

        val action = Action(Constants.EOSCONTRACT_EOSIO, Constants.ACTION_NEWACCOUNT)
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
    private fun getRequiredFee(signedTransaction: SignedTransaction) {
        HttpUtils.postRequest(BaseUrl.HTTP_get_required_fee, this, mGson.toJson(GetRequiredFee(signedTransaction)), object : JsonCallback<ResponseRequiredFee>() {
            override fun onSuccess(response: Response<ResponseRequiredFee>) {
                if (response.body() != null) {
                    val requireFee = response.body()
                    signedTransaction.fee = requireFee.required_fee
                    signAndPushTransaction(signedTransaction)
                } else {
                    ToastUtils.showLong(response.body().toString())
                }
            }
        })
    }

    //六签名并推送交易
    private fun signAndPushTransaction(signedTransaction: SignedTransaction) {
        signedTransaction.sign(EosPrivateKey(creatorPriKey), TypeChainId(chainInfo.chain_id))
        val packedTransactionCreateEos = PackedTransactionCreateEos(signedTransaction)
        HttpUtils.postRequest(BaseUrl.HTTP_push_transaction, this, mGson.toJson(packedTransactionCreateEos), object : JsonCallback<ResponsePushTransaction>() {
            override fun onSuccess(response: Response<ResponsePushTransaction>) {
                if (response.body() != null) {
                    val pushSuccessBean = response.body()
                    ToastUtils.showLong("新建账号提交成功"+pushSuccessBean.transaction_id)

                } else {
                    ToastUtils.showLong(response.body().toString())
                }
            }

        })
    }


}
