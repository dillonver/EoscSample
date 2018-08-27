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
 * Auto-generated: 2018-08-24 15:25:56
 *
 * @author dillon
 */
class ChainBlock : Serializable {

    var timestamp: String? = null
    var producer: String? = null
    var confirmed: Int = 0
    var previous: String? = null
    var transaction_mroot: String? = null
    var action_mroot: String? = null
    var schedule_version: Int = 0
    var new_producers: String? = null
    var header_extensions: List<String>? = null
    var producer_signature: String? = null
    var transactions: List<Transaction>? = null
    var block_extensions: List<String>? = null
    var id: String? = null
    var block_num: Int = 0
    var ref_block_prefix: Long = 0
    fun getTimeAfterHeadBlockTime(diffInMilSec: Int): String? {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        try {
            var date = sdf.parse(this.timestamp)

            val c = Calendar.getInstance()
            c.time = date
            c.add(Calendar.MILLISECOND, diffInMilSec)
            date = c.time

            return sdf.format(date)

        } catch (e: ParseException) {
            e.printStackTrace()
            return this.timestamp
        }

    }

    fun getNowTimeAfterExpration(diffInMilSec: Int): String {
        val mExpration = System.currentTimeMillis() + diffInMilSec
        val date = Date(mExpration)
        val sdf = "yyyy-MM-dd'T'HH:mm:ss"
        val format = SimpleDateFormat(sdf)
        return format.format(date)

    }

    // 将字符串转为时间戳
    fun getTime(user_time: String): String? {
        var re_time: String? = null
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val d: Date
        try {
            d = sdf.parse(user_time)
            val l = d.time
            val str = l.toString()
            re_time = str.substring(0, 10)
        } catch (e: ParseException) {
            // TODO Auto-generated catch block e.printStackTrace();
        }

        return re_time
    }
}