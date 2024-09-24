package dev.ses.berserk.utils

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.entity.Player
import javax.rmi.CORBA.Util

enum class Sounds(private val sound8: String?, private val sound912: String?, private val sound13: String?) {

    EXPLODE("EXPLODE", "ENTITY_GENERIC_EXPLODE"),
    NOTE_PLING("NOTE_PLING", "BLOCK_NOTE_PLING", "BLOCK_NOTE_BLOCK_PLING"),
    VILLAGER_NO("VILLAGER_NO", "ENTITY_VILLAGER_NO"),
    VILLAGER_YES("VILLAGER_YES", "ENTITY_VILLAGER_YES");


    private var cachedSound: Sound? = null


    constructor(sound8: String?, sound913: String?) : this(sound8, sound913, sound913)

    constructor(sound13: String?) : this(null, null, sound13)

    @JvmOverloads
    fun play(player: Player, volume: Float = 0.25f, pitch: Float = 1f) {
        player.playSound(player.location, sound!!, volume, pitch)
    }

    fun play(player: Player, location: Location, volume: Float = 0.25f, pitch: Float = 1f) {
        player.playSound(location, sound!!, volume, pitch)
    }

    private val sound: Sound?
        get() {
            if (cachedSound != null) {
                return cachedSound
            }
            return if (Utils.VERSION.contains("1.8")) {
                (if (sound8 == null) Sound.valueOf("IRONGOLEM_DEATH") else Sound.valueOf(sound8)).also {
                    cachedSound = it
                }
            } else if (Utils.VERSION.contains("1.9") || Utils.VERSION.contains("1.10") || Utils.VERSION.contains("1.11") || Utils.VERSION.contains(
                    "1.12"
                )
            ) {
                (if (sound912 == null) Sound.valueOf("ENTITY_IRONGOLEM_DEATH") else Sound.valueOf(sound912)).also {
                    cachedSound = it
                }
            } else if (Utils.VERSION.contains("1.13")) {
                (if (sound13 == null) Sound.valueOf("ENTITY_IRON_GOLEM_DEATH") else Sound.valueOf(sound13)).also {
                    cachedSound = it
                }
            } else {
                null
            }
        }
}
