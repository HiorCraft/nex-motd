package dev.hiorcraft.nex.motd.velocity.redis

import dev.hiorcraft.nex.motd.api.redis.MotdProfileChangeEvent
import dev.hiorcraft.nex.motd.velocity.config.MotdConfig
import dev.slne.surf.redis.event.OnRedisEvent

object MotdRedisListener {

    @OnRedisEvent
    fun onProfileChange(event: MotdProfileChangeEvent) {
        MotdConfig.edit { activeProfile = event.profile }
    }
}
