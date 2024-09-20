package dev.ses.berserk.ability.type

import dev.ses.berserk.BerserkPlugin
import dev.ses.berserk.ability.Ability
import dev.ses.berserk.utils.CC
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent

abstract class DamageableAbility(name : String?) : Ability(name) {

    var hits : Int? = null

    init {
        hits = BerserkPlugin.getInstance()!!.getAbilitiesFile()!!.getInt("ABILITIES.$name.HITS")
    }

    open fun onDamage(event: EntityDamageByEntityEvent) {
        setCooldown(player = event.damager as Player)
        decreaseItem(player = event.damager as Player)
        for (message in getExecuteMessage()!!){
            event.damager.sendMessage(CC.translate(message))
        }
    }

}