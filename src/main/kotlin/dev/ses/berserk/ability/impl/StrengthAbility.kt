package dev.ses.berserk.ability.impl

import dev.ses.berserk.BerserkPlugin
import dev.ses.berserk.ability.type.InteractAbility
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class StrengthAbility : InteractAbility("STRENGTH") {

    override fun getExecuteMessage(): MutableList<String>? {
        return BerserkPlugin.getInstance()?.getLangFile()?.getStringList("ABILITIES."+getName()+".EXECUTE.STRENGTH")
    }

    override fun getEffects(): MutableList<PotionEffect> {
        return mutableListOf(PotionEffect(PotionEffectType.INCREASE_DAMAGE, 6*20, 1))
    }


    override fun onInteract(event: PlayerInteractEvent){
        val player = event.player
        getEffects().forEach {potionEffect -> player.addPotionEffect(potionEffect)}
        super.onInteract(event)
    }

}