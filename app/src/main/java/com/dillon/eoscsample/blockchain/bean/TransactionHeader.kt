package com.dillon.eoscsample.blockchain.bean

import com.google.gson.annotations.Expose
import com.dillon.eoscsample.blockchain.cypto.util.BitUtils
import com.dillon.eoscsample.blockchain.cypto.util.HexUtils
import com.dillon.eoscsample.blockchain.types.EosType

import java.math.BigInteger
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

/**
 * Created by dillon
 */
open class TransactionHeader : EosType.Packer {
    @Expose
    var expiration: String? = null

    @Expose
    var ref_block_num = 0
        private set // uint16_t

    @Expose
    var ref_block_prefix: Long = 0
        private set// uint32_t

    @Expose
    private var max_net_usage_words: Long = 0 // fc::unsigned_int

    @Expose
    private var max_cpu_usage_ms: Long = 0    // fc::unsigned_int

    @Expose
    private var delay_sec: Long = 0     // fc::unsigned_int


    constructor() {}

    constructor(other: TransactionHeader) {
        this.expiration = other.expiration
        this.ref_block_num = other.ref_block_num
        this.ref_block_prefix = other.ref_block_prefix
        this.max_net_usage_words = other.max_net_usage_words
        this.max_cpu_usage_ms = other.max_cpu_usage_ms
        this.delay_sec = other.delay_sec
    }

    fun setReferenceBlock(refBlockIdAsSha256: String) {
        ref_block_num = BigInteger(1, HexUtils.toBytes(refBlockIdAsSha256.substring(0, 8))).toInt() and 0xffff

        ref_block_prefix = //new BigInteger( 1, HexUtils.toBytesReversed( refBlockIdAsSha256.substring(16,24))).longValue();
                BitUtils.uint32ToLong(HexUtils.toBytes(refBlockIdAsSha256.substring(16, 24)), 0) // BitUtils treats bytes in little endian.
        // so, no need to reverse bytes.
    }

    fun setReferenceBlock(block_num: Int, mref_block_prefix: Long) {
        ref_block_num = block_num

        ref_block_prefix = mref_block_prefix
    }


    private fun getExpirationAsDate(dateStr: String?): Date {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        try {
            sdf.timeZone = TimeZone.getTimeZone("UTC")
            return sdf.parse(dateStr)

        } catch (e: ParseException) {
            e.printStackTrace()
            return Date()
        }

    }

    fun putNetUsageWords(netUsage: Long) {
        this.max_net_usage_words = netUsage
    }

    fun putKcpuUsage(kCpuUsage: Long) {
        this.max_cpu_usage_ms = kCpuUsage
    }

    override fun pack(writer: EosType.Writer) {
        writer.putIntLE((getExpirationAsDate(expiration).time / 1000).toInt()) // ms -> sec

        writer.putShortLE((ref_block_num and 0xFFFF).toShort())  // uint16
        writer.putIntLE((ref_block_prefix and -0x1).toInt())// uint32

        // fc::unsigned_int
        writer.putVariableUInt(max_net_usage_words)
        writer.putVariableUInt(max_cpu_usage_ms)
        writer.putVariableUInt(delay_sec)
    }

    override fun toString(): String {
        return "TransactionHeader(expiration=$expiration, ref_block_num=$ref_block_num, ref_block_prefix=$ref_block_prefix, max_net_usage_words=$max_net_usage_words, max_cpu_usage_ms=$max_cpu_usage_ms, delay_sec=$delay_sec)"
    }
}
