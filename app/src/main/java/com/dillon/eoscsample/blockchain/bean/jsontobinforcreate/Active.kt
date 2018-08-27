/**
 * Copyright 2018 bejson.com
 */
package com.dillon.eoscsample.blockchain.bean.jsontobinforcreate

import java.util.ArrayList

/**
 * Auto-generated: 2018-08-24 17:57:28
 *
 * @author dillon
 */
class Active {

    var threshold = 1
    var keys: List<Keys>? = null
    var accounts: List<String> = ArrayList()
    var waits: List<String> = ArrayList()

    override fun toString(): String {
        return "Active{" +
                "threshold=" + threshold +
                ", keys=" + keys +
                ", accounts=" + accounts +
                ", waits=" + waits +
                '}'.toString()
    }

    fun setKeys(keys: List<Keys>): Active {
        this.keys = keys
        return this
    }


}