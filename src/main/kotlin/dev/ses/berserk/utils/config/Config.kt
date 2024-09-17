package dev.ses.berserk.utils.config

import dev.ses.berserk.BerserkPlugin
import dev.ses.berserk.utils.ConfigFile

class Config {

    companion object{
        var IS_GLOBAL_COOLDOWN : Boolean? = false
        var GLOBAL_COOLDOWN_TIME : Int? = null
        var IS_APOLLO_API : Boolean? = false
    }

    init {

        val config : ConfigFile? = BerserkPlugin.getInstance()!!.getConfigFile()
        IS_GLOBAL_COOLDOWN = config?.getBoolean("GLOBAL-COOLDOWN.ENABLED")
        GLOBAL_COOLDOWN_TIME = config?.getInt("GLOBAL-COOLDOWN.TIME")
        IS_APOLLO_API = config?.getBoolean("APOLLO_API")

    }
}