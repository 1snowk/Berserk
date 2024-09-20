package dev.ses.berserk.utils

import com.google.common.collect.HashBasedTable
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.TimeUnit


class CooldownAPI {

    companion object{

        private val cooldowns: HashBasedTable<UUID, String, Long> = HashBasedTable.create()

        fun isInGlobalCooldown(player: Player): Boolean {
            return isInCooldown(player, "GLOBAL")
        }

        fun isInCooldown(player: Player, cooldownName: String?): Boolean {
            return cooldowns.contains(player.uniqueId, cooldownName) && cooldowns.get(
                player.uniqueId,
                cooldownName
            ) !!> System.currentTimeMillis()
        }

        fun addCooldown(player: Player, cooldownName: String, time: Int) {
            addCooldown(player, cooldownName, TimeUnit.SECONDS.toMillis(time.toLong()))
        }

        private fun addCooldown(player: Player, cooldownName: String, time: Long) {
            if (time == 0L) {
                cooldowns.remove(player.getUniqueId(), cooldownName)
                return
            }

            if (time < 0L) {
                cooldowns.remove(player.getUniqueId(), cooldownName)
                return
            }

            cooldowns.put(player.getUniqueId(), cooldownName, time + System.currentTimeMillis())
        }


        fun getCooldown(player: Player, cooldownName: String?): String {
            val timeLeft : Long = cooldowns.get(player.uniqueId, cooldownName) !!- System.currentTimeMillis()
            return format(timeLeft)
        }

        private fun format(time: Long): String {
            var millis = time
            if (millis <= 0L) return "0 seconds"

            val days: Long = TimeUnit.MILLISECONDS.toDays(millis)
            millis -= TimeUnit.DAYS.toMillis(days)

            val hours: Long = TimeUnit.MILLISECONDS.toHours(millis)
            millis -= TimeUnit.HOURS.toMillis(hours)

            val minutes: Long = TimeUnit.MILLISECONDS.toMinutes(millis)
            millis -= TimeUnit.MINUTES.toMillis(minutes)

            val seconds: Long = TimeUnit.MILLISECONDS.toSeconds(millis)

            val builder = StringBuilder()

            if (days > 0L) {
                builder.append(days).append(" days ")
            }
            if (hours > 0L) {
                builder.append(hours).append(" hours ")
            }
            if (minutes > 0L) {
                builder.append(minutes).append(" minutes ")
            }
            if (seconds > 0L) {
                builder.append(seconds).append(" seconds ")
            }
            return builder.toString().trim()
        }
    }


}
