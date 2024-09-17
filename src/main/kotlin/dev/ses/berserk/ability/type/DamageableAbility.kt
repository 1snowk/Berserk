package dev.ses.berserk.ability.type

import dev.ses.berserk.ability.Ability
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent

abstract class DamageableAbility(name : String?) : Ability(name) {


    protected fun onDamage(event: EntityDamageByEntityEvent) {
        setCooldown(player = event.damager as Player)

    }
}