package dev.ses.berserk.ability

import dev.ses.berserk.BerserkPlugin
import dev.ses.berserk.ability.type.InteractAbility
import dev.ses.berserk.utils.CC
import dev.ses.berserk.utils.CooldownAPI
import dev.ses.berserk.utils.config.Lang
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class AbilityListener : Listener {

    init {
        BerserkPlugin.getInstance()?.let { Bukkit.getPluginManager().registerEvents(this, it) }
    }


    @EventHandler
    private fun onInteract(event: PlayerInteractEvent) {
        val player = event.player
        val item = event.item

        if (!event.action.name.startsWith("RIGHT_")) return

        if (item == null) return

        val ability: Ability? = BerserkPlugin.getInstance()?.getManager()?.getAbilityByItem(item)

        if (ability != null) {

            if (ability !is InteractAbility) return

            val interactAbility: InteractAbility = ability
            event.isCancelled = true

            if (interactAbility.isEnd() == true && player.world.environment == World.Environment.THE_END){
                player.sendMessage(CC.translate(Lang.ABILITY_END!!))
                return
            }

            if (interactAbility.isNether() == true && player.world.environment == World.Environment.NETHER) {
                player.sendMessage(CC.translate(Lang.ABILITY_NETHER!!))
                return
            }

            if (CooldownAPI.isInCooldown(player, "GLOBAL")){
                player.sendMessage(CC.translate(BerserkPlugin.getInstance()!!.getLangFile()?.getString("GLOBAL-COOLDOWN")?.replace("{time}", CooldownAPI.getCooldown(player, "GLOBAL"))!!))
                return
            }

            val abilityName : String = interactAbility.getName()!!

            if (CooldownAPI.isInCooldown(player, abilityName)){
                player.sendMessage(CC.translate(BerserkPlugin.getInstance()!!.getLangFile()?.getString("ABILITIES.IN-COOLDOWN")?.replace("{time}", CooldownAPI.getCooldown(player, abilityName))!!))
                return
            }

            interactAbility.onInteract(event)
        }
    }
}