package dev.ses.berserk.ability.runnable

import dev.ses.berserk.BerserkPlugin
import dev.ses.berserk.ability.Ability
import dev.ses.berserk.utils.ActionBar
import dev.ses.berserk.utils.CC
import dev.ses.berserk.utils.CooldownAPI
import dev.ses.berserk.utils.Utils
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import java.util.concurrent.TimeUnit
import kotlin.math.ceil

class AbilityRunnable(plugin: BerserkPlugin) : Runnable {

    private var id : Int = 0

    init {
        this.id = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, this, 0L, 0L).taskId
    }


    override fun run() {
        for (players in Bukkit.getServer().onlinePlayers) {

            val itemInHand : ItemStack = if (Utils.VERSION.contains("1.8") || Utils.VERSION.contains("1.7")) {
                players.itemInHand
            }else{
                players.inventory.itemInMainHand
            }

            if (itemInHand.type == Material.AIR) {
                return
            }

            val ability: Ability? = BerserkPlugin.getInstance().getManager()!!.getAbilityByItem(itemInHand)


            if (ability != null) {

                if (!CooldownAPI.isInCooldown(players, ability.getName())){
                    return
                }

                val icon = "▊"
                val time = TimeUnit.SECONDS.toMillis(ability.getTime()!!.toLong())/1000

                val icons : Int = ceil(((CooldownAPI.cooldowns.get(players.uniqueId, ability.getName()) !!- System.currentTimeMillis()) / 1000.0) / time*10).toInt()
                val builder = StringBuilder()

                for (i in 0 until 10){
                    if (i < icons){
                        builder.append(icon).append("c&")
                    }else{
                        builder.append(icon).append("a&")
                    }
                }
                ActionBar.sendActionBar(players, CC.translate("${ability.getDisplayName()} &8&l ${builder.reverse()} &f ${CooldownAPI.getCooldown(players, ability.getName())}"))
            }
        }
    }
    private fun cancelTask(){
        Bukkit.getScheduler().cancelTask(id)
    }
}
