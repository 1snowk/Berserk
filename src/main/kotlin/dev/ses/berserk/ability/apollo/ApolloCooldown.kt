package dev.ses.berserk.ability.apollo

import com.lunarclient.apollo.common.icon.ItemStackIcon
import com.lunarclient.apollo.module.cooldown.Cooldown
import java.time.Duration


class ApolloCooldown (name : String?, itemMaterial : String?, itemData : Int?) {

    private var name : String? = null
    private var itemMaterial : String? = null
    private var itemData : Int? = null

    init {
        this.name = name
        this.itemMaterial = itemMaterial
        this.itemData = itemData
    }

    fun createCooldown(time : Int?): Cooldown {
        return Cooldown.builder().name(name).duration(Duration.ofSeconds(time!!.toLong())).icon(ItemStackIcon.builder().itemName(this.itemMaterial!!).itemId(this.itemData!!).build()).build()
    }
}