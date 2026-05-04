package dev.hiorcraft.nex.motd.paper.redis

import dev.hiorcraft.nex.motd.api.redis.MotdProfileChangeEvent
import dev.hiorcraft.nex.motd.paper.config.MotdConfig
import dev.slne.surf.redis.annotation.OnRedisEvent

object MotdRedisListener {

    @OnRedisEvent
    fun onProfileChange(event: MotdProfileChangeEvent) {
        MotdConfig.edit { activeProfile = event.profile }
    }
}
