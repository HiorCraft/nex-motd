import dev.slne.surf.api.gradle.util.slneReleases

buildscript {
    repositories {
        gradlePluginPortal()
        maven("https://reposilite.slne.dev/releases")
    }
    dependencies {
        classpath("dev.slne.surf.api:surf-api-gradle-plugin:+")
    }
}

allprojects {
    group = "dev.hiorcraft.nex.motd"
    version = findProperty("version") as String
}

subprojects {
    plugins.withType<PublishingPlugin> {
        configure<PublishingExtension> {
            repositories {
                slneReleases()
            }
        }
    }
}