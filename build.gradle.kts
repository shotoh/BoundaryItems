plugins {
    java
}

group = "io.github.shotoh.boundaryitems"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://oss.sonatype.org/content/repositories/central")
}

dependencies {
    compileOnly("org.spigotmc:spigot:1.8.8-R0.1-SNAPSHOT")
    implementation("org.incendo:cloud-bukkit:2.0.0-beta.2")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}