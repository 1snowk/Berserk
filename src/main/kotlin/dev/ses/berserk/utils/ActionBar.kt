package dev.ses.berserk.utils

import org.bukkit.Bukkit
import org.bukkit.entity.Player

class ActionBar {


    companion object{
        private var nmsVersion: String? = null

        fun sendActionBar(player: Player?, message: String) {
            if (nmsVersion == null) {
                nmsVersion = (Bukkit.getServer().javaClass.getPackage().name.also { nmsVersion = it }).substring(
                    nmsVersion!!.lastIndexOf(".") + 1
                )
            }
            try {
                val c1 = Class.forName("org.bukkit.craftbukkit.$nmsVersion.entity.CraftPlayer")
                val p = c1.cast(player)
                val ppoc: Any
                val c2: Class<*>
                val c3: Class<*>
                val c4 = Class.forName("net.minecraft.server.$nmsVersion.PacketPlayOutChat")
                val c5 = Class.forName("net.minecraft.server.$nmsVersion.Packet")
                val o: Any
                if ((nmsVersion.equals(
                        "v1_8_R1",
                        ignoreCase = true
                    ) || !nmsVersion!!.startsWith("v1_8_")) && !nmsVersion!!.startsWith("v1_9_")
                ) {
                    c2 = Class.forName("net.minecraft.server.$nmsVersion.ChatSerializer")
                    c3 = Class.forName("net.minecraft.server.$nmsVersion.IChatBaseComponent")
                    val m3 = c2.getDeclaredMethod("a", String::class.java)
                    o = c3.cast(m3.invoke(c2, "{\"text\": \"$message\"}"))
                } else {
                    c2 = Class.forName("net.minecraft.server.$nmsVersion.ChatComponentText")
                    c3 = Class.forName("net.minecraft.server.$nmsVersion.IChatBaseComponent")
                    o = c2.getConstructor(*arrayOf<Class<*>>(String::class.java)).newInstance(message)
                }
                ppoc = c4.getConstructor(*arrayOf(c3, Byte::class.javaPrimitiveType)).newInstance(o, 2.toByte())
                val m1 = c1.getDeclaredMethod("getHandle")
                val h = m1.invoke(p)
                val f1 = h.javaClass.getDeclaredField("playerConnection")
                val pc = f1[h]
                val m5 = pc.javaClass.getDeclaredMethod("sendPacket", c5)
                m5.invoke(pc, ppoc)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }
}
