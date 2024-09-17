package dev.ses.berserk.ability

import com.lunarclient.apollo.Apollo
import com.lunarclient.apollo.module.cooldown.CooldownModule
import com.lunarclient.apollo.player.ApolloPlayer
import com.lunarclient.apollo.recipients.Recipients
import dev.ses.berserk.BerserkPlugin
import dev.ses.berserk.ability.apollo.ApolloCooldown
import dev.ses.berserk.utils.CooldownAPI
import dev.ses.berserk.utils.ItemBuilder
import dev.ses.berserk.utils.config.Config
import dev.ses.berserk.utils.config.Lang
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import java.util.Optional


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
        this.displayName = BerserkPlugin.getInstance()!!.getAbilitiesFile()!!.getString("ABILITIES.$name.DISPLAY-NAME")
        this.material = BerserkPlugin.getInstance()!!.getAbilitiesFile()!!.getString("ABILITIES.$name.ITEM.MATERIAL")
        this.data = BerserkPlugin.getInstance()!!.getAbilitiesFile()!!.getInt("ABILITIES.$name.ITEM.DATA")
        this.time = BerserkPlugin.getInstance()!!.getAbilitiesFile()!!.getInt("ABILITIES.$name.COOLDOWN")
        this.lore = BerserkPlugin.getInstance()!!.getAbilitiesFile()!!.getStringList("ABILITIES.$name.ITEM.LORE")
        this.nether = BerserkPlugin.getInstance()!!.getAbilitiesFile()!!.getBoolean("ABILITIES.$name.BLOCKED-IN.NETHER")
        this.end = BerserkPlugin.getInstance()!!.getAbilitiesFile()!!.getBoolean("ABILITIES.$name.BLOCKED-IN.END")
        this.item = ItemBuilder(Material.getMaterial(this.material!!)!!, 1, this.data!!).name(this.displayName!!).lore(lore!!).toStack()
    }


    fun setCooldown(player: Player) {
        CooldownAPI.addCooldown(player, this.name!!, this.time!!)

        if (Config.IS_GLOBAL_COOLDOWN == true){
            CooldownAPI.addCooldown(player, "GLOBAL", Config.GLOBAL_COOLDOWN_TIME!!)
        }

        if (Config.IS_APOLLO_API == true && Bukkit.getPluginManager().getPlugin("Apollo-Bukkit") != null) {
            val aCooldown = ApolloCooldown(this.name, this.material, this.data!!)
            val apolloPlayer: Optional<ApolloPlayer> = Apollo.getPlayerManager().getPlayer(player.uniqueId)
            apolloPlayer.ifPresent {
                Apollo.getModuleManager().getModule(CooldownModule::class.java)
                    .displayCooldown(apolloPlayer.get(), aCooldown.createCooldown(this.time))

            }
        }
    }

    fun getMaterial(): String?{
        return material
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

    fun isAbility(item : ItemStack?): Boolean{
        val abilityItem: ItemStack = this.item as ItemStack

        if (item == null) return false

        if (item.type != abilityItem.type) return false

        if (!item.hasItemMeta()) return false

        val abilityMeta = abilityItem.itemMeta
        val itemMeta = item.itemMeta

        return itemMeta!!.hasDisplayName() && itemMeta.displayName.equals(
            abilityMeta!!.displayName,
            ignoreCase = true
        ) && (itemMeta.hasLore() == abilityMeta.hasLore()) && itemMeta.lore == abilityMeta.lore
    }

    fun decreaseItem(player: Player){
        if (player.itemInHand.amount == 1){
            player.setItemInHand(null)
        }else{
            player.itemInHand.amount -= 1
        }
    }

    abstract fun getExecuteMessage(): MutableList<String>?
    abstract fun getEffects(): MutableList<PotionEffect>?

    companion object {

        fun isAbility(item: ItemStack?, abilityItem: ItemStack?): Boolean {
            if (item == null || abilityItem == null) return false

            if (item.type != abilityItem.type) return false

            if (!item.hasItemMeta()) return false

            val abilityMeta = abilityItem.itemMeta
            val itemMeta = item.itemMeta

            return itemMeta!!.hasDisplayName() && itemMeta.displayName.equals(
                abilityMeta!!.displayName,
                ignoreCase = true
            ) && (itemMeta.hasLore() == abilityMeta.hasLore()) && itemMeta.lore == abilityMeta.lore
        }
    }
}