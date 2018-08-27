/**
 * Copyright 2018 bejson.com
 */
package com.dillon.eoscsample.blockchain.bean.jsontobinforcreate

/**
 * Auto-generated: 2018-08-24 17:57:28
 *
 * @author dillon
 */
class Keys {

    var key: String? = null
    var weight = 1
    fun setKey(key: String): Keys {
        this.key = key
        return this
    }


    override fun toString(): String {
        return "Keys{" +
                "key='" + key + '\''.toString() +
                ", weight=" + weight +
                '}'.toString()
    }
}