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
class Action_traces : Serializable {

    var receipt: Receipt? = null
    var act: Act? = null
    var elapsed: Int = 0
    var cpu_usage: Int = 0
    var console: String? = null
    var total_cpu_usage: Int = 0
    var trx_id: String? = null
    var inline_traces: List<String>? = null

}