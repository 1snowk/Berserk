package dev.ses.berserk.ability.impl

import dev.ses.berserk.BerserkPlugin
import dev.ses.berserk.ability.type.InteractAbility
import dev.ses.berserk.utils.Utils
import org.bukkit.Bukkit
import org.bukkit.entity.Egg
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class RageBallAbility : InteractAbility("RAGE-BALL"), Listener {

    init {
        Bukkit.getPluginManager().registerEvents(this, BerserkPlugin.getInstance())
    }

    override fun getEffects(): MutableList<PotionEffect> {
        return mutableListOf(Utils.createPotionEffect(PotionEffectType.INCREASE_DAMAGE, 10, 2),
            Utils.createPotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 10, 3))
    }

    private fun getNegativeEffects(): MutableList<PotionEffect>{
        return mutableListOf(
            Utils.createPotionEffect(PotionEffectType.BLINDNESS, 10, 5),
            Utils.createPotionEffect(PotionEffectType.POISON, 10, 5),
            Utils.createPotionEffect(PotionEffectType.WEAKNESS, 10, 5))
    }

    override fun onInteract(event: PlayerInteractEvent) {
        val player = event.player
        val egg : Egg = player.launchProjectile(Egg::class.java)

        egg.setMetadata("rageBall", FixedMetadataValue(BerserkPlugin.getInstance(), true))
        super.onInteract(event)
    }


    @EventHandler
    private fun onProjectileHit(event: ProjectileHitEvent) {
        if (event.entity !is Egg) {return}

        val egg : Egg = event.entity as Egg

        if (!egg.hasMetadata("rageBall")) {return}

        val shooter = egg.shooter as Player

        for (entities in egg.getNearbyEntities(5.0, 5.0, 5.0)) {
            if (entities == null){
                return
            }

            if (entities !is Player) {
                continue
            }
            Utils.giveEffectsToPlayer(entities, getNegativeEffects())
        }
        Utils.createFakeExplosion(egg)
        Utils.giveEffectsToPlayer(shooter, getEffects())
    }
}
