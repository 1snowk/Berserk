package dev.ses.berserk.ability

import dev.ses.berserk.BerserkPlugin
import dev.ses.berserk.ability.type.DamageableAbility
import dev.ses.berserk.ability.type.InteractAbility
import dev.ses.berserk.utils.CC
import dev.ses.berserk.utils.CooldownAPI
import dev.ses.berserk.utils.config.Lang
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerInteractEvent

class AbilityListener : Listener {

    init {
        Bukkit.getPluginManager().registerEvents(this, BerserkPlugin.getInstance())
    }

//    @EventHandler
//    private fun onDamageByEntity(event : EntityDamageByEntityEvent) {
//        if (event.damager !is Player || event.entity !is Player) return
//
//        val damager = event.damager as Player
//        val entity = event.entity as Player
//
//        val item = damager.itemInHand ?: return
//
//        val ability = BerserkPlugin.getInstance().getManager()!!.getAbilityByItem(item)
//
//        if (ability != null) {
//            if (ability !is DamageableAbility) {
//                return
//            }
//
//            val damageableAbility : DamageableAbility = ability
//
//            if (damageableAbility.isEnd() == true && damager.world.environment != World.Environment.THE_END || entity.world.environment != World.Environment.THE_END) {
//                damager.sendMessage(CC.translate(Lang.ABILITY_END!!))
//                return
//            }
//
//            if (damageableAbility.isNether() == true && damager.world.environment != World.Environment.NETHER || entity.world.environment != World.Environment.NETHER) {
//                damager.sendMessage(CC.translate(Lang.ABILITY_END!!))
//                return
//            }
//
//            if (CooldownAPI.isInGlobalCooldown(damager)){
//                damager.sendMessage(CC.translate(BerserkPlugin.getInstance()!!.getLangFile()?.getString("GLOBAL-COOLDOWN")?.replace("{time}", CooldownAPI.getCooldown(damager, "GLOBAL"))!!))
//                return
//            }
//
//            if (CooldownAPI.isInCooldown(damager, damageableAbility.getName())){
//                damager.sendMessage(CC.translate(BerserkPlugin.getInstance()!!.getLangFile()?.getString("ABILITIES.IN-COOLDOWN")?.replace("{time}", CooldownAPI.getCooldown(damager, damageableAbility.getName()))!!))
//                return
//            }
//
//            var hitsCont = 0
//
//            if (hitsCont <= damageableAbility.hits!!){
//                damager.sendMessage(CC.translate("&cHits: $hitsCont/${damageableAbility.hits}"))
//                return
//            }else{
//                hitsCont = 0
//                damageableAbility.onDamage(event)
//            }
//            hitsCont += 1
//        }
//    }


    @EventHandler
    private fun onInteract(event: PlayerInteractEvent) {
        val player = event.player
        val item = event.item

        if (!event.action.name.startsWith("RIGHT_")) return

        if (item == null) return

        val ability: Ability? = BerserkPlugin.getInstance().getManager()!!.getAbilityByItem(item)

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
            if (CooldownAPI.isInGlobalCooldown(player)){
                player.sendMessage(CC.translate(BerserkPlugin.getInstance().getLangFile()?.getString("GLOBAL-COOLDOWN")?.replace("{time}", CooldownAPI.getCooldown(player, "GLOBAL"))!!))
                return
            }

            if (CooldownAPI.isInCooldown(player, interactAbility.getName())){
                player.sendMessage(CC.translate(BerserkPlugin.getInstance().getLangFile()?.getString("ABILITIES.IN-COOLDOWN")?.replace("{time}", CooldownAPI.getCooldown(player, interactAbility.getName()))!!))
                return
            }

            interactAbility.onInteract(event)
        }
    }
}