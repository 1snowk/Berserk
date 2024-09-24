package dev.ses.berserk.ability.impl

import dev.ses.berserk.ability.type.InteractAbility
import dev.ses.berserk.utils.Utils
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class JumpBoostAbility : InteractAbility("JUMP-BOOST") {


    override fun getEffects(): MutableList<PotionEffect>{
        return mutableListOf(Utils.createPotionEffect(PotionEffectType.JUMP, 6, 4))
    }

    override fun onInteract(event: PlayerInteractEvent) {
        Utils.giveEffectsToPlayer(event.player, getEffects())
        super.onInteract(event)
    }
}