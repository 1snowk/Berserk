package dev.ses.berserk.ability.command

import dev.ses.berserk.BerserkPlugin
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class AbilityCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val player : Player = sender as Player

        for (abilities in BerserkPlugin.getInstance()!!.getManager()!!.getAbilityList()){
              player.inventory.addItem(abilities.getAbilityItem())
        }
        return false
    }
}