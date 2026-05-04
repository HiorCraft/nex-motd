package dev.hiorcraft.nex.motd.api.redis

import dev.slne.surf.redis.event.RedisEvent
import kotlinx.serialization.Serializable

@Serializable
data class MotdProfileChangeEvent(val profile: String) : RedisEvent()
