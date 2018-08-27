/**
 * Copyright 2018 bejson.com
 */
package com.dillon.eoscsample.blockchain.bean

import java.io.Serializable
import java.util.Date

/**
 * Auto-generated: 2018-08-27 14:32:19
 *
 * @author dillon
 */
class Receipt  : Serializable {

    var receiver: String? = null
    var act_digest: String? = null
    var global_sequence: Long = 0
    var recv_sequence: Long = 0
    var code_sequence: Int = 0
    var abi_sequence: Int = 0

}