package dev.ses.berserk.ability.impl

import dev.ses.berserk.BerserkPlugin
import dev.ses.berserk.ability.type.InteractAbility
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.potion.PotionEffect
import java.util.*

class PrePearlAbility : InteractAbility("PRE-PEARL") {

    override fun getEffects(): MutableList<PotionEffect>? {
        return null
    }

    private val locationMap : MutableMap<UUID, Location> = mutableMapOf()

    override fun onInteract(event: PlayerInteractEvent) {
        locationMap[event.player.uniqueId] = event.player.location

        Bukkit.getScheduler().runTaskLater(BerserkPlugin.getInstance()!!, Runnable {
            val lastLocation : Location? = locationMap.remove(event.player.uniqueId)
            event.player.teleport(lastLocation!!)
        }, 10*20L)

        super.onInteract(event)
    }
}
