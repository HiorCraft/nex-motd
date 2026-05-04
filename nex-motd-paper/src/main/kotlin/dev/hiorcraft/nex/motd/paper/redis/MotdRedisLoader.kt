package dev.hiorcraft.nex.motd.paper.redis

import dev.hiorcraft.nex.motd.paper.config.MotdConfig
import dev.slne.surf.redis.RedisApi
import dev.slne.surf.redis.sync.value.SyncValue
import kotlin.time.Duration.Companion.days

val redisLoader = MotdRedisLoader()
val redisApi get() = redisLoader.redisApi

class MotdRedisLoader {
    lateinit var redisApi: RedisApi
        private set
    lateinit var activeProfile: SyncValue<String>
        private set

    fun connect() {
        redisApi = RedisApi.create()
        redisApi.subscribeToEvents(MotdRedisListener)
        activeProfile = redisApi.createSyncValue(
            id = "motd:activeProfile",
            defaultValue = MotdConfig.getConfig().activeProfile,
            ttl = 365.days
        )
        redisApi.freezeAndConnect()
    }

    fun disconnect() {
        redisApi.disconnect()
    }
}
