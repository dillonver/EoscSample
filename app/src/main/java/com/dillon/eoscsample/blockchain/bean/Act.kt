/**
 * Copyright 2018 bejson.com
 */
package com.dillon.eoscsample.blockchain.bean

import java.io.Serializable

/**
 * Auto-generated: 2018-08-27 14:32:19
 *
 * @author dillon
 */
class Act :Serializable{

    var account: String? = null
    var name: String? = null
    var authorization: List<Authorization>? = null
    var hex_data: String? = null

}