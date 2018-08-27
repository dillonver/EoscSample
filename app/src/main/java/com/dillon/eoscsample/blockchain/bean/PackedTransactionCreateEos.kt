package com.dillon.eoscsample.blockchain.bean

import com.google.gson.annotations.Expose
import com.dillon.eoscsample.blockchain.cypto.util.HexUtils
import com.dillon.eoscsample.blockchain.types.EosByteWriter

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.Serializable
import java.util.zip.DataFormatException
import java.util.zip.Deflater
import java.util.zip.Inflater

/**
 * Created by dillon
 */

class PackedTransactionCreateEos @JvmOverloads constructor(@field:Expose
                                                           var transaction: SignedTransaction, compressType: CompressType = CompressType.none) : Serializable {

    @Expose
    var signatures: MutableList<String>? = null

    @Expose
    val compression: String

    enum class CompressType {
        none, zlib
    }


    init {
        compression = compressType.name
        signatures = transaction.signatures

    }


    override fun toString(): String {
        return "PackedTransactionCreateEos{" +
                "signatures=" + signatures +
                ", compression='" + compression + '\''.toString() +
                ", transaction=" + transaction +
                '}'.toString()
    }

    private fun packContextFreeData(ctxFreeData: List<String>?, compressType: CompressType): ByteArray {
        val byteWriter = EosByteWriter(64)

        val ctxFreeDataCount = ctxFreeData?.size ?: 0
        if (ctxFreeDataCount == 0) {
            return byteWriter.toBytes()
        }

        byteWriter.putVariableUInt(ctxFreeDataCount.toLong())

        for (hexData in ctxFreeData!!) {
            byteWriter.putBytes(HexUtils.toBytes(hexData))
        }

        return compress(byteWriter.toBytes(), compressType)
    }

    private fun compress(uncompressedBytes: ByteArray, compressType: CompressType?): ByteArray {
        if (compressType == null || CompressType.zlib != compressType) {
            return uncompressedBytes
        }

        // zip!
        val deflater = Deflater(Deflater.BEST_COMPRESSION)
        deflater.setInput(uncompressedBytes)

        val outputStream = ByteArrayOutputStream(uncompressedBytes.size)
        deflater.finish()
        val buffer = ByteArray(1024)
        while (!deflater.finished()) {
            val count = deflater.deflate(buffer) // returns the generated code... index
            outputStream.write(buffer, 0, count)
        }

        try {
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
            return uncompressedBytes
        }

        return outputStream.toByteArray()
    }

    private fun decompress(compressedBytes: ByteArray): ByteArray {
        val inflater = Inflater()
        inflater.setInput(compressedBytes)

        val outputStream = ByteArrayOutputStream(compressedBytes.size)
        val buffer = ByteArray(1024)

        try {
            while (!inflater.finished()) {
                val count = inflater.inflate(buffer)
                outputStream.write(buffer, 0, count)
            }
            outputStream.close()
        } catch (e: DataFormatException) {
            e.printStackTrace()
            return compressedBytes
        } catch (e: IOException) {
            e.printStackTrace()
            return compressedBytes
        }


        return outputStream.toByteArray()
    }
}

