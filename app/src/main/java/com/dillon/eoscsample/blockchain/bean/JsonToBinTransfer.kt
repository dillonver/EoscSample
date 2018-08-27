package com.dillon.eoscsample.blockchain.bean

import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.google.gson.annotations.Expose
import java.io.Serializable

/**
 * Created by swapnibble on 2017-09-12.
 */

class JsonToBinTransfer(@field:Expose
                        private val code: String, @field:Expose
                        private val action: String, args: String) : Serializable {

    @Expose
    private var args: JsonElement? = null

    val argsString: String
        get() = args!!.asString

    init {
        this.args = JsonParser().parse(args)
    }

    fun putArgs(args: String) {
        this.args = JsonParser().parse(args)
    }
}
