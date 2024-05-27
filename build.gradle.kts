plugins {
    java
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "io.github.shotoh.boundaryitems"
version = "1.0.4-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://oss.sonatype.org/content/repositories/central")
    maven("https://jitpack.io")
}

dependencies {
    compileOnly("org.spigotmc:spigot:1.8.8-R0.1-SNAPSHOT")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7") {
        exclude("org.bukkit")
    }
    implementation("org.incendo:cloud-paper:2.0.0-beta.2")
    implementation("xyz.xenondevs:particle:1.8.4")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}