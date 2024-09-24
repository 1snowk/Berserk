package dev.ses.berserk.ability.impl

import dev.ses.berserk.BerserkPlugin
import dev.ses.berserk.ability.type.InteractAbility
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.potion.PotionEffect
import kotlin.random.Random

class ZeusAbility : InteractAbility("ZEUS"), Listener {


    init {
        Bukkit.getPluginManager().registerEvents(this, BerserkPlugin.getInstance())
    }

    override fun getEffects(): MutableList<PotionEffect>? {
        return null
    }

    override fun onInteract(event: PlayerInteractEvent) {
        event.player.setMetadata("zeus", FixedMetadataValue(BerserkPlugin.getInstance()!!, true))

        Bukkit.getScheduler().runTaskLater(BerserkPlugin.getInstance(), Runnable {
            event.player.removeMetadata("zeus", BerserkPlugin.getInstance())
        }, 10*20L)

        super.onInteract(event)
    }

    @EventHandler
    private fun onDamage(event : EntityDamageByEntityEvent){
        if (event.damager !is Player || event.entity !is Player){
            return
        }

        val damager = event.damager as Player
        val damaged = event.entity as Player

        if (!damager.hasMetadata("zeus")) return

        if (Random.nextInt(0, 100) <= 20){
            damaged.world.strikeLightningEffect(damaged.location)
            if (damaged.health < 4){
                damaged.health = 0.0
                return
            }
            damaged.health -= 4
        }
    }
}