package dev.ses.berserk

import com.google.common.base.Strings
import dev.ses.berserk.ability.AbilityListener
import dev.ses.berserk.ability.command.AbilityCommand
import dev.ses.berserk.ability.runnable.AbilityRunnable
import dev.ses.berserk.manager.Manager
import dev.ses.berserk.utils.CC
import dev.ses.berserk.utils.ConfigFile
import dev.ses.berserk.utils.config.Config
import dev.ses.berserk.utils.config.Lang
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class BerserkPlugin : JavaPlugin() {

    private var manager : Manager? = null
    private var configFile : ConfigFile? = null
    private var langFile : ConfigFile? = null
    private var abilitiesFile : ConfigFile? = null


    override fun onEnable() {
        this.manager = Manager()
        this.configFile = ConfigFile("config.yml")
        this.langFile = ConfigFile("lang.yml")
        this.abilitiesFile = ConfigFile("abilities.yml")
        Lang()
        Config()
        log("&2" + Strings.repeat("=", 40))
        log("&a&lBerserk - v0.1")
        log(" ")
        log("&eAuthor: &fsnowk")
        log("&eDiscord Support: &fmavzm")
        this.manager!!.loadAbilities()
        log("&2" + Strings.repeat("=", 40))
        this.getCommand("ability")!!.setExecutor(AbilityCommand())
        AbilityListener()
        AbilityRunnable(this)
    }

    override fun onDisable() {

    }

    fun log(text : String){
        Bukkit.getConsoleSender().sendMessage(CC.translate(text))
    }

    fun getManager() : Manager? {
        return this.manager
    }

    fun getConfigFile() : ConfigFile? {
        return this.configFile
    }

    fun getLangFile() : ConfigFile? {
        return this.langFile
    }

    fun getAbilitiesFile() : ConfigFile? {
        return this.abilitiesFile
    }

    companion object {

        fun getInstance(): BerserkPlugin {
            return getPlugin(BerserkPlugin::class.java)
        }
    }
}
