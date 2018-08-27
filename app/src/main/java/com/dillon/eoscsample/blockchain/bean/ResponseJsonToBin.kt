/**
 * Copyright 2018 bejson.com
 */
package com.dillon.eoscsample.blockchain.bean

import java.io.Serializable
import java.util.ArrayList

/**
 * Auto-generated: 2018-08-24 15:46:42
 *
 * @author dillon
 */
class ResponseJsonToBin : Serializable {

    var binargs: String? = null
    var required_scope: List<String>? = null
        get() = if (field == null) {
            ArrayList()
        } else field
    var required_auth: List<String>? = null
        get() = if (field == null) {
            ArrayList()
        } else field

}