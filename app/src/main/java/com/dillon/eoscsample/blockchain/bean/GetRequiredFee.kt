/**
 * Copyright 2018 bejson.com
 */
package com.dillon.eoscsample.blockchain.bean

import com.google.gson.annotations.Expose
import com.dillon.eoscsample.blockchain.bean.SignedTransaction

import java.io.Serializable

/**
 * Auto-generated: 2018-08-24 15:38:7
 *
 * @author dillon
 */
class GetRequiredFee(@field:Expose
                     var transaction: SignedTransaction?) : Serializable {

    override fun toString(): String {
        return "GetRequiredFee{" +
                "transaction=" + transaction +
                '}'.toString()
    }

}