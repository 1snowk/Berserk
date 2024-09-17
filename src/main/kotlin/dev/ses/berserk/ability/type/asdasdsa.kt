package dev.ses.berserk.ability.type

import com.lunarclient.apollo.Apollo
import com.lunarclient.apollo.module.cooldown.Cooldown
import com.lunarclient.apollo.module.cooldown.CooldownModule
import com.lunarclient.apollo.player.ApolloPlayer
import java.util.*

class asdasdsa {
    fun sendApolloCooldown(uuid: UUID?, cooldown: Cooldown?) {
        val apolloPlayerOptional = Apollo.getPlayerManager().getPlayer(uuid)
        apolloPlayerOptional.ifPresent { apolloPlayer: ApolloPlayer? ->
            Apollo.getModuleManager().getModule(
                CooldownModule::class.java
            ).displayCooldown(apolloPlayer, cooldown)
        }
    }
}
