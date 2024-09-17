package dev.ses.berserk.ability.type

import dev.ses.berserk.ability.Ability
import dev.ses.berserk.utils.CC
import org.bukkit.event.player.PlayerInteractEvent

abstract class InteractAbility(name: String?) : Ability(name) {

    open fun onInteract(event: PlayerInteractEvent) {
        decreaseItem(event.player)
        setCooldown(event.player)
        for (messages in getExecuteMessage()!!){
            event.player.sendMessage(CC.translate(messages))
        }
    }

}