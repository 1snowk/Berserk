package dev.ses.berserk.ability.impl

import dev.ses.berserk.ability.type.DamageableAbility
import dev.ses.berserk.utils.CooldownAPI
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.potion.PotionEffect

class ExoticBone : DamageableAbility("EXOTIC-BONE") {

    override fun getEffects(): MutableList<PotionEffect>? {
        return null
    }

    override fun onDamage(event: EntityDamageByEntityEvent) {
        val entity = event.entity as Player
        CooldownAPI.addCooldown(entity, "ANTI-TRAP", 10)
        super.onDamage(event)
    }
}