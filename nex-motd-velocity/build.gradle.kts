plugins {
    id("dev.slne.surf.api.gradle.velocity")
}

velocityPluginFile {
    main = "dev.hiorcraft.nex.motd.velocity.NexMotdVelocityPlugin"

    authors.add("HiorCraft")
}

dependencies {
    implementation(project(":nex-motd-api"))
}
