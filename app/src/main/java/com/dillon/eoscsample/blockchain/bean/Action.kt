package com.dillon.eoscsample.blockchain.bean

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.annotations.Expose
import com.dillon.eoscsample.blockchain.cypto.util.HexUtils
import com.dillon.eoscsample.blockchain.types.EosType
import com.dillon.eoscsample.blockchain.types.TypeAccountName
import com.dillon.eoscsample.blockchain.types.TypeActionName
import com.dillon.eoscsample.blockchain.types.TypePermissionLevel

import java.util.ArrayList
import java.util.Arrays

/**
 * Created by dillon
 */

class Action @JvmOverloads constructor(account: String? = null, name: String? = null, authorization: TypePermissionLevel? = null, data: String? = null) : EosType.Packer {
    @Expose
    private var account: TypeAccountName? = null

    @Expose
    private var name: TypeActionName? = null

    @Expose
    private var authorization: MutableList<TypePermissionLevel>? = null

    @Expose
    var data: JsonElement? = null
        private set

    init {
        this.account = TypeAccountName(account)
        this.name = TypeActionName(name)
        this.authorization = ArrayList()
        if (null != authorization) {
            this.authorization!!.add(authorization)
        }

        if (null != data) {
            this.data = JsonPrimitive(data)
        }
    }

    fun getAccount(): String {
        return account!!.toString()
    }

    fun setAccount(account: String) {
        this.account = TypeAccountName(account)
    }

    fun getName(): String {
        return name!!.toString()
    }

    fun setName(name: String) {
        this.name = TypeActionName(name)
    }

    fun getAuthorization(): List<TypePermissionLevel>? {
        return authorization
    }

    fun setAuthorization(authorization: MutableList<TypePermissionLevel>) {
        this.authorization = authorization
    }

    fun setAuthorization(authorization: Array<TypePermissionLevel>) {
        this.authorization!!.addAll(Arrays.asList(*authorization))
    }

    fun setAuthorization(accountWithPermLevel: Array<String>?) {
        if (null == accountWithPermLevel) {
            return
        }

        for (permissionStr in accountWithPermLevel) {
            val split = permissionStr.split("@".toRegex(), 2).toTypedArray()
            authorization!!.add(TypePermissionLevel(split[0], split[1]))
        }
    }

    fun setData(data: String) {
        this.data = JsonPrimitive(data)
    }

    override fun pack(writer: EosType.Writer) {
        account!!.pack(writer)
        name!!.pack(writer)

        writer.putCollection(authorization)

        if (null != data) {
            val dataAsBytes = HexUtils.toBytes(data!!.asString)
            writer.putVariableUInt(dataAsBytes.size.toLong())
            writer.putBytes(dataAsBytes)
        } else {
            writer.putVariableUInt(0)
        }
    }
}