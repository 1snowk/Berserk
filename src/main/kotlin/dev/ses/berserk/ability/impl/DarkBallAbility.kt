package dev.ses.berserk.ability.impl

import dev.ses.berserk.ability.type.InteractAbility
import dev.ses.berserk.utils.Sounds
import dev.ses.berserk.utils.Utils
import org.bukkit.Bukkit
import org.bukkit.Effect
import org.bukkit.Particle
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class DarkBallAbility : InteractAbility("DARK-BALL") {

    override fun getEffects(): MutableList<PotionEffect> {
        return mutableListOf(Utils.createPotionEffect(PotionEffectType.INCREASE_DAMAGE, 5, 2),
            Utils.createPotionEffect(PotionEffectType.REGENERATION, 5, 3),
            Utils.createPotionEffect(PotionEffectType.ABSORPTION, 5, 4),
            Utils.createPotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 5, 3))
    }

    private fun getNegativeEffects(): MutableList<PotionEffect>{
        return mutableListOf(Utils.createPotionEffect(PotionEffectType.BLINDNESS, 10, 5),
            Utils.createPotionEffect(PotionEffectType.POISON, 10, 5),
            Utils.createPotionEffect(PotionEffectType.WEAKNESS, 10, 5))
    }

    override fun onInteract(event: PlayerInteractEvent) {
        val player = event.player
        for (nearbyEntities in player.getNearbyEntities(15.0, 15.0, 15.0)){
            if (nearbyEntities !is Player) continue
            Utils.giveEffectsToPlayer(nearbyEntities, getNegativeEffects())
        }


        Utils.createFakeExplosion(player)
        Utils.giveEffectsToPlayer(player, getEffects())

        super.onInteract(event)
    }
}