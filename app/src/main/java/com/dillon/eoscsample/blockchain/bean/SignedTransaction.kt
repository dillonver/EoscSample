package com.dillon.eoscsample.blockchain.bean

import com.google.gson.annotations.Expose
import com.dillon.eoscsample.blockchain.cypto.digest.Sha256
import com.dillon.eoscsample.blockchain.cypto.ec.EcDsa
import com.dillon.eoscsample.blockchain.cypto.ec.EcSignature
import com.dillon.eoscsample.blockchain.cypto.ec.EosPrivateKey
import com.dillon.eoscsample.blockchain.types.EosByteWriter
import com.dillon.eoscsample.blockchain.types.TypeChainId

import java.io.Serializable
import java.util.ArrayList

/**
 * Created by dillon
 */

class SignedTransaction : Transaction, Serializable {

    @Expose
    var signatures: MutableList<String>? = null

    @Expose
    var ctxFreeData: List<String>? = ArrayList()

    val ctxFreeDataCount: Int
        get() = ctxFreeData?.size ?: 0


    constructor() : super() {}

    constructor(anotherTxn: SignedTransaction) : super(anotherTxn) {
        this.signatures = deepCopyOnlyContainer(anotherTxn.signatures)
        this.ctxFreeData = ctxFreeData
    }



    fun putSignatures(signatures: MutableList<String>) {
        this.signatures = signatures
    }


    private fun getDigestForSignature(chainId: TypeChainId): Sha256 {
        val writer = EosByteWriter(255)

        // data layout to sign :
        // [ {chainId}, {Transaction( parent class )}, {hash of context_free_data only when exists ]

        writer.putBytes(chainId.bytes)
        pack(writer)
        if (ctxFreeData!!.size > 0) {
        } else {
            writer.putBytes(Sha256.ZERO_HASH.bytes)
        }
        return Sha256.from(writer.toBytes())
    }


    override fun toString(): String {
        return "SignedTransaction{" +
                "signatures=" + signatures +
                ", context_free_data=" + ctxFreeData +
                '}'.toString()
    }

    fun sign(privateKey: EosPrivateKey, chainId: TypeChainId): SignedTransaction {
        if (null == this.signatures) {
            this.signatures = ArrayList()
        }

        val signature = EcDsa.sign(getDigestForSignature(chainId), privateKey)
        this.signatures!!.add(signature.toString())
        return this
    }
}

