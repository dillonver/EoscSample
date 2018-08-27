package com.dillon.eoscsample.utils

import com.dillon.eoscsample.blockchain.cypto.ec.EosPrivateKey

import java.util.ArrayList


object PublicAndPrivateKeyUtils {


    fun getPrivateKey(count: Int): Array<EosPrivateKey?> {
        val retKeys = arrayOfNulls<EosPrivateKey>(count)
        for (i in 0 until count) {
            retKeys[i] = EosPrivateKey()
        }

        return retKeys
    }

}
