package dev.ses.berserk.manager

import dev.ses.berserk.ability.Ability
import dev.ses.berserk.ability.impl.*
import dev.ses.berserk.utils.CC
import org.bukkit.Bukkit
import org.bukkit.inventory.ItemStack

class Manager {

    private val abilitiesList : MutableList<Ability> = mutableListOf()

    fun loadAbilities() {
        this.abilitiesList.add(StrengthAbility())
        this.abilitiesList.add(ResistanceAbility())
        this.abilitiesList.add(JumpBoostAbility())
        this.abilitiesList.add(PrePearlAbility())
        this.abilitiesList.add(ZeusAbility())
        this.abilitiesList.add(DarkBallAbility())
        this.abilitiesList.add(ExoticBoneAbility())
        Bukkit.getConsoleSender().sendMessage(CC.translate("&aHas successfully loaded " + abilitiesList.size.toString() + " abilities."))
    }

    fun getAbilityByItem(item : ItemStack) : Ability? {
        return abilitiesList.firstOrNull{ ability ->  ability.isAbility(item)}
    }

    fun getAbilityList(): MutableList<Ability> {
        return abilitiesList
    }


}