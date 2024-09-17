package dev.ses.berserk.utils

import org.bukkit.ChatColor

class CC {

    companion object{
        fun translate(text: String): String{
            return ChatColor.translateAlternateColorCodes('&', text)
        }

        fun translate(texts: MutableList<String>): MutableList<String> {
            val toReturn : MutableList<String> = mutableListOf()
            for (text in texts){
                toReturn.add(translate(text))
            }
            return toReturn
        }
    }

}