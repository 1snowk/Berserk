package dev.ses.berserk.utils.config

import dev.ses.berserk.BerserkPlugin

class Lang {

    companion object{
        var ABILITY_NETHER : String? = null
        var ABILITY_END : String? = null
        var NO_PERMS : String? = null
    }

    init {
        val langFile = BerserkPlugin.getInstance()?.getLangFile()

        ABILITY_NETHER = langFile?.getString("ABILITIES.BLOCKED.NETHER")
        ABILITY_NETHER = langFile?.getString("ABILITIES.BLOCKED.END")
        NO_PERMS = langFile?.getString("NO-PERMS")
    }
}