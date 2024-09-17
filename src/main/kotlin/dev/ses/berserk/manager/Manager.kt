package dev.ses.berserk.manager

import dev.ses.berserk.ability.Ability
import dev.ses.berserk.ability.impl.StrengthAbility
import dev.ses.berserk.utils.CC
import org.bukkit.Bukkit
import org.bukkit.inventory.ItemStack

class Manager {

    private val abilitesList : MutableList<Ability> = mutableListOf()

    fun loadAbilites() {
        abilitesList.add(StrengthAbility())

        Bukkit.getConsoleSender().sendMessage(CC.translate("&aHas successfully loaded " + abilitesList.size.toString() + " abilities."))
    }

    fun getAbilityByItem(item : ItemStack) : Ability? {
        return abilitesList.firstOrNull{ ability ->  ability.isAbility(item)}
    }

    fun getAbilityList(): MutableList<Ability> {
        return abilitesList
    }


}