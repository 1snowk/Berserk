package dev.ses.berserk.ability.impl

import dev.ses.berserk.ability.type.InteractAbility
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class ResistanceAbility : InteractAbility("RESISTANCE") {

    override fun getEffects(): MutableList<PotionEffect> {
        return mutableListOf(PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 6*20, 2))
    }

    override fun onInteract(event: PlayerInteractEvent) {
        for (effect in getEffects()){
            for (activeEffects in event.player.activePotionEffects){
                if (activeEffects.type == effect.type){
                    event.player.removePotionEffect(effect.type)
                }else{
                    event.player.addPotionEffect(effect)
                }
            }
        }
        super.onInteract(event)
    }
}