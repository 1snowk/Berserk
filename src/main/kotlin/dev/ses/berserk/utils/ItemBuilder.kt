package dev.ses.berserk.utils


import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class ItemBuilder {

    private val item : ItemStack

    constructor(material: Material){
        item = ItemStack(material)
    }

    constructor(material: Material, amount : Int, data : Int){
        item = ItemStack(material)
        item.amount = amount
        item.durability = data.toShort()
    }

    fun name(text: String): ItemBuilder {
        val metaItem : ItemMeta? = item.itemMeta
        metaItem?.setDisplayName(CC.translate(text))
        item.setItemMeta(metaItem)
        return this
    }

    fun lore(lore : MutableList<String>): ItemBuilder {
        val metaItem : ItemMeta? = item.itemMeta
        metaItem?.lore = CC.translate(lore)
        item.setItemMeta(metaItem)
        return this
    }

    fun toStack() : ItemStack {
        return item
    }




}