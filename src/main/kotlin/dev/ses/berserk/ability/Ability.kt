package dev.ses.berserk.ability


import dev.ses.berserk.BerserkPlugin

import dev.ses.berserk.utils.CooldownAPI
import dev.ses.berserk.utils.ItemBuilder
import dev.ses.berserk.utils.config.Config
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect


abstract class Ability(private var name: String?) {

     private var displayName : String? = null
     private var material : String? = null
     private var data : Int? = null
     private var time : Int? = null
     private var item : ItemStack? = null
     private var lore : MutableList<String>? = null
     private var nether : Boolean? = null
     private var end : Boolean? = null

    init {
        this.displayName = BerserkPlugin.getInstance().getAbilitiesFile()!!.getString("ABILITIES.$name.DISPLAY-NAME")
        this.material = BerserkPlugin.getInstance().getAbilitiesFile()!!.getString("ABILITIES.$name.ITEM.MATERIAL")
        this.data = BerserkPlugin.getInstance().getAbilitiesFile()!!.getInt("ABILITIES.$name.ITEM.DATA")
        this.time = BerserkPlugin.getInstance().getAbilitiesFile()!!.getInt("ABILITIES.$name.COOLDOWN")
        this.lore = BerserkPlugin.getInstance().getAbilitiesFile()!!.getStringList("ABILITIES.$name.ITEM.LORE")
        this.nether = BerserkPlugin.getInstance().getAbilitiesFile()!!.getBoolean("ABILITIES.$name.BLOCKED-IN.NETHER")
        this.end = BerserkPlugin.getInstance().getAbilitiesFile()!!.getBoolean("ABILITIES.$name.BLOCKED-IN.END")
        this.item = ItemBuilder(Material.getMaterial(this.material!!)!!, 1, this.data!!).name(this.displayName!!).lore(lore!!).toStack()
    }


    fun setCooldown(player: Player) {
        CooldownAPI.addCooldown(player, this.name!!, this.time!!)
        if (Config.IS_GLOBAL_COOLDOWN == true){
            CooldownAPI.addCooldown(player, "GLOBAL", Config.GLOBAL_COOLDOWN_TIME!!)
        }
    }

    fun getMaterial(): String?{
        return material
    }

    fun getDisplayName(): String?{
        return displayName
    }

    fun isNether(): Boolean? {
        return this.nether
    }

    fun isEnd(): Boolean? {
        return this.end
    }

    fun getName(): String? {
        return this.name
    }

    fun getAbilityItem(): ItemStack? {
        return this.item
    }

    fun getTime(): Int? {
        return this.time
    }

    fun isAbility(item : ItemStack?): Boolean{
        return (item != null) && (item.type == getAbilityItem()!!.type) && item.hasItemMeta()
                && item.itemMeta!!.hasDisplayName()
                && item.itemMeta!!.displayName.equals(getAbilityItem()!!.itemMeta!!.displayName, ignoreCase = true) &&
                (item.itemMeta!!.hasLore() == getAbilityItem()!!.itemMeta!!.hasLore())
                && item.itemMeta!!.lore == getAbilityItem()!!.itemMeta!!.lore
    }

    fun decreaseItem(player: Player){
        if (player.itemInHand.amount == 1){
            player.setItemInHand(null)
        }else{
            player.itemInHand.amount -= 1
        }
    }

     fun getExecuteMessage(): MutableList<String>?{
         return BerserkPlugin.getInstance().getLangFile()?.getStringList("ABILITIES.EXECUTE.$this.name")
     }

    abstract fun getEffects(): MutableList<PotionEffect>?
}