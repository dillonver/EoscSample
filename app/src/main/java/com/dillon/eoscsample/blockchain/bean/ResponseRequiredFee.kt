package com.dillon.eoscsample.blockchain.bean

import com.google.gson.annotations.Expose

import java.io.Serializable

/**
 * Created by dillon
 */

class ResponseRequiredFee(@field:Expose
                          var required_fee: String?) : Serializable
