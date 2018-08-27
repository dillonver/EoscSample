/**
 * Copyright 2018 bejson.com
 */
package com.dillon.eoscsample.blockchain.bean

import java.io.Serializable

/**
 * Auto-generated: 2018-08-26 17:40:13
 *
 * @author dillon
 */
class Processed : Serializable {

    var id: String? = null
    var receipt: Receipt? = null
    var elapsed: Int = 0
    var net_usage: Int = 0
    var scheduled: Boolean = false
    var action_traces: List<Action_traces>? = null
    var except: String? = null

}