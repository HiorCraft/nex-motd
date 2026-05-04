plugins {
    id("dev.slne.surf.api.gradle.velocity")
}

velocityPluginFile {
    main = "dev.hiorcraft.nex.motd.velocity.NexMotdVelocityPlugin"
}

dependencies {
    implementation(project(":nex-motd-api"))
}
