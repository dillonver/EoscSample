/**
 * Copyright 2018 bejson.com
 */
package com.dillon.eoscsample.blockchain.bean

import java.io.Serializable
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

/**
 * Auto-generated: 2018-08-24 15:22:16
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
class ChainInfo : Serializable {

    var server_version: String? = null
    var chain_id: String? = null
    var head_block_num: String? = null
    var last_irreversible_block_num: Long = 0
    var last_irreversible_block_id: String? = null
    var head_block_id: String? = null
    var head_block_time: String? = null
    var head_block_producer: String? = null
    var virtual_block_cpu_limit: Long = 0
    var virtual_block_net_limit: Long = 0
    var block_cpu_limit: Long = 0
    var block_net_limit: Long = 0
    fun getTimeAfterHeadBlockTime(diffInMilSec: Int): String? {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        try {
            var date = sdf.parse(this.head_block_time)

            val c = Calendar.getInstance()
            c.time = date
            c.add(Calendar.MILLISECOND, diffInMilSec)
            date = c.time

            return sdf.format(date)

        } catch (e: ParseException) {
            e.printStackTrace()
            return this.head_block_time
        }

    }
}