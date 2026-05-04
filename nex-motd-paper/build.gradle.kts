import dev.slne.surf.api.gradle.util.registerRequired

plugins {
    id("dev.slne.surf.api.gradle.paper-plugin")
}

surfPaperPluginApi {
    mainClass("dev.hiorcraft.nex.motd.paper.NexMotdPaperPlugin")
    serverDependencies {
        registerRequired("LuckPerms")
        registerRequired("MiniPlaceholders")
    }
}

dependencies {
    compileOnly("net.luckperms:api:5.4")
    compileOnly("io.github.miniplaceholders:miniplaceholders-api:3.0.1")
    compileOnly("io.github.miniplaceholders:miniplaceholders-kotlin-ext:3.0.1")
    implementation(project(":nex-motd-api"))
}
