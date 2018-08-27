/**
 * Copyright 2018 bejson.com
 */
package com.dillon.eoscsample.blockchain.bean.jsontobinforcreate

/**
 * Auto-generated: 2018-08-24 17:57:28
 *
 * @author dillon
 */
class Args {

    private var creator: String? = null
    private var name: String? = null
    private var owner: Owner? = null
    private var active: Active? = null
    fun setCreator(creator: String): Args {
        this.creator = creator
        return this
    }

    fun getCreator(): String? {
        return creator
    }

    fun setName(name: String): Args {
        this.name = name
        return this
    }

    fun getName(): String? {
        return name
    }

    fun setOwner(owner: Owner): Args {
        this.owner = owner
        return this

    }

    fun getOwner(): Owner? {
        return owner
    }

    override fun toString(): String {
        return "Args{" +
                "creator='" + creator + '\''.toString() +
                ", name='" + name + '\''.toString() +
                ", owner=" + owner +
                ", active=" + active +
                '}'.toString()
    }

    fun setActive(active: Active): Args {
        this.active = active
        return this
    }

    fun getActive(): Active? {
        return active
    }

}