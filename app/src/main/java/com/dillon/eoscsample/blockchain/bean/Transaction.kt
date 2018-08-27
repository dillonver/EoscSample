package com.dillon.eoscsample.blockchain.bean

import com.google.gson.annotations.Expose
import com.dillon.eoscsample.blockchain.types.EosType
import com.dillon.eoscsample.blockchain.types.TypeAsset
import com.dillon.eoscsample.blockchain.util.Utils

import java.io.Serializable
import java.util.ArrayList

/**
 * Created by dillon
 */

open class Transaction : TransactionHeader, Serializable {
    @Expose
    private var context_free_actions = ArrayList<Action>()

    @Expose
    private var actions: MutableList<Action>? = ArrayList()

    @Expose
    private var transaction_extensions = ArrayList<String>()
    @Expose
    var fee: String? = null     // fc::unsigned_int

    val contextFreeActionCount: Int
        get() = if (actions == null) 0 else actions!!.size

    constructor() : super() {}


    constructor(other: Transaction) : super(other) {
        this.context_free_actions = deepCopyOnlyContainer(other.context_free_actions)!!
        this.actions = deepCopyOnlyContainer(other.actions)
        this.transaction_extensions = other.transaction_extensions
    }

    fun addAction(msg: Action) {
        if (null == actions) {
            actions = ArrayList(1)
        }

        actions!!.add(msg)
    }


    fun getActions(): List<Action>? {
        return actions
    }

    fun setActions(actions: MutableList<Action>) {
        this.actions = actions
    }


    internal fun <T> deepCopyOnlyContainer(srcList: List<T>?): ArrayList<T>? {
        if (null == srcList) {
            return null
        }

        val newList = ArrayList<T>(srcList.size)
        newList.addAll(srcList)

        return newList
    }

    override fun pack(writer: EosType.Writer) {
        super.pack(writer)

        writer.putCollection(context_free_actions)
        writer.putCollection(actions)
        //writer.putCollection(transaction_extensions);
        writer.putVariableUInt(transaction_extensions.size.toLong())
        val f = (10000 * Utils.matchFloat(fee)).toLong()
        val asset = TypeAsset(f)
        writer.putLongLE(asset.amount)
        writer.putLongLE(asset.assetSymbol())
        if (transaction_extensions.size > 0) {
            // TODO 구체적 코드가 나오면 확인후 구현할 것.
        }
    }
}
