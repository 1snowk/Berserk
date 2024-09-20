package dev.ses.berserk.ability.command

import com.google.common.base.Strings
import dev.ses.berserk.BerserkPlugin
import dev.ses.berserk.ability.Ability
import dev.ses.berserk.utils.CC
import dev.ses.berserk.utils.Sounds
import dev.ses.berserk.utils.config.Lang
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class AbilityCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        if(sender !is Player) {
            sender.sendMessage(CC.translate("&cThis command is only executable in game!."))
            return true
        }

        if (!sender.hasPermission("berserk.admin")) {
            sender.sendMessage(CC.translate(Lang.NO_PERMS!!))
            return true
        }


        if (args.isEmpty()) {
            sender.sendMessage(CC.translate("&7&m" + Strings.repeat("-", 40)))
            sender.sendMessage(CC.translate("&a&lBerserk Abilities"))
            sender.sendMessage("")
            sender.sendMessage(CC.translate("&a* &2/ability list"))
            sender.sendMessage(CC.translate("&a* &2/ability get <ability> <amount>"))
            sender.sendMessage(CC.translate("&a* &2/ability getall"))
            sender.sendMessage("")
            sender.sendMessage(CC.translate("&7&m" + Strings.repeat("-", 40)))
           return true
        }

        if (args[0].equals("list", ignoreCase = true)) {
            for (abilities in BerserkPlugin.getInstance()!!.getManager()!!.getAbilityList()){
                sender.sendMessage(CC.translate("${abilities.getDisplayName()} &7: ${abilities.getName()}"))
            }
            return true
        }

        if (args[0].equals("getall", true)){
            for (abilities in BerserkPlugin.getInstance()!!.getManager()!!.getAbilityList()){
                sender.inventory.addItem(abilities.getAbilityItem())
            }
            Sounds.NOTE_PLING.play(sender)
        }

        if (args[0].equals("get", true)){
            if (args.size < 3) {
                sender.sendMessage(CC.translate("&cUsage: /$label get <ability> <amount>"))
                return true
            }

            val ability = BerserkPlugin.getInstance()!!.getManager()!!.getAbilityByName(args[1])
            val amount = getOrOne(args[2])

            if (ability == null){
                sender.sendMessage(CC.translate("&cUsage: /$label get <ability> <amount>"))
                return true
            }

            val itemStack = ability.getAbilityItem()!!.clone()
            itemStack.amount = amount
            sender.inventory.addItem(itemStack)
            Sounds.NOTE_PLING.play(sender)
            return true
        }
        return false
    }

    private fun getOrOne(string: String) : Int{
        try {
            return Integer.parseInt(string)
        }catch (e : NumberFormatException){
            e.printStackTrace()
            return 1
        }
    }
}
