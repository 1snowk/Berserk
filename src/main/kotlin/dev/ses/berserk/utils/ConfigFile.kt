package dev.ses.berserk.utils

import dev.ses.berserk.BerserkPlugin
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.IOException

class ConfigFile : YamlConfiguration {

    val file : File

    constructor(name: String)  {
        this.file = File(BerserkPlugin.getInstance()?.dataFolder, name)
        if (!this.file.exists()) {
            BerserkPlugin.getInstance()?.saveResource(name, false)
        }

        try {
            this.load(this.file)
        }catch (e: IOException) {
            e.printStackTrace()
            throw RuntimeException(e)
        }
    }

    fun save(){
        try {
            this.save(file)
        }catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun reload(){
        try {
            this.load(file)
        }catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun getSection(name: String): ConfigurationSection? {
        return super.getConfigurationSection(name)
    }
}