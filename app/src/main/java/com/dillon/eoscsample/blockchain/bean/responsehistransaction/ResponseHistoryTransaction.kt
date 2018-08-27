/**
 * Copyright 2018 bejson.com
 */
package com.dillon.eoscsample.blockchain.bean.responsehistransaction

import java.util.Date

/**
 * Auto-generated: 2018-08-27 16:59:0
 *
 * @author dillon
 */
class ResponseHistoryTransaction {

    var id: String? = null
    var trx: String? = null
    var block_time: Date? = null
    var block_num: Int = 0
    var last_irreversible_block: Long = 0
    var traces: List<Traces>? = null

}