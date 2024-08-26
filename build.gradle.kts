plugins {
    id("java-library")
    id("io.github.kota65535.dependency-report") version "2.0.1"
    id("com.github.johnrengelman.shadow") version ("8.1.1")
}

allprojects {
    group = "io.github.subkek.customdiscs"
    version = "1.4.9"
}

java.sourceCompatibility = JavaVersion.VERSION_16
java.targetCompatibility = JavaVersion.VERSION_16
java.disableAutoTargetJvm()

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.dmulloy2.net/repository/public/")
    maven("https://maven.maxhenkel.de/repository/public")
    maven("https://jitpack.io") {
        content {
            includeModule("me.carleslc.Simple-YAML", "Simple-Yaml")
            includeModule("me.carleslc.Simple-YAML", "Simple-Configuration")
            includeModule("me.carleslc.Simple-YAML", "Simple-YAML-Parent")
            includeModule("com.github.technicallycoded", "FoliaLib")
        }
    }
    maven("https://maven.lavalink.dev/releases")
    maven("https://maven.lavalink.dev/snapshots")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
    //compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
 
    compileOnly("de.maxhenkel.voicechat:voicechat-api:2.5.0")
    compileOnly("com.comphenix.protocol:ProtocolLib:5.3.0-SNAPSHOT")

    shadow("com.googlecode.soundlibs:mp3spi:1.9.5.4")
    shadow("org.jflac:jflac-codec:1.5.2")
    shadow("commons-io:commons-io:2.16.1")
    shadow("com.github.technicallycoded:FoliaLib:0.4.3")
    shadow("dev.lavalink.youtube:common:1.7.2")
    shadow("dev.arbjerg:lavaplayer:2.2.1") {
        exclude("org.slf4j")
    }

    shadow(platform("net.kyori:adventure-bom:4.17.0"))
    shadow("net.kyori:adventure-api")
    shadow("net.kyori:adventure-text-minimessage")
    shadow("net.kyori:adventure-platform-bukkit:4.3.4")

    shadow("org.yaml:snakeyaml:2.2")
    shadow ("me.carleslc.Simple-YAML:Simple-Yaml:1.8.4") {
        exclude(group="org.yaml", module="snakeyaml")
    }

    compileOnly("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")
}

tasks.jar {
    enabled = false
}

tasks.build {
    dependsOn(tasks.shadowJar)
}

tasks.shadowJar {
    archiveFileName = "${rootProject.name}-$version.jar"

    configurations = listOf(project.configurations.shadow.get())
    mergeServiceFiles()

    relocate("org.apache", "io.github.subkek.customdiscs.libs.org.apache")
    relocate("org.jsoup", "io.github.subkek.customdiscs.libs.org.jsoup")
    relocate("com.fasterxml", "io.github.subkek.customdiscs.libs.com.fasterxml")
    relocate("org.yaml.snakeyaml", "io.github.subkek.customdiscs.libs.org.yaml.snakeyaml")
    relocate("org.simpleyaml", "io.github.subkek.customdiscs.libs.org.simpleyaml")
    relocate("org.jflac", "io.github.subkek.customdiscs.libs.org.jflac")
    relocate("org.json", "io.github.subkek.customdiscs.libs.org.json")
    //relocate("org.mozilla", "io.github.subkek.customdiscs.libs.org.mozilla")
    relocate("org.tritonus", "io.github.subkek.customdiscs.libs.org.tritonus")
    relocate("mozilla", "io.github.subkek.customdiscs.libs.mozilla")
    relocate("junit", "io.github.subkek.customdiscs.libs.junit")
    relocate("javazoom", "io.github.subkek.customdiscs.libs.javazoom")
    relocate("certificates", "io.github.subkek.customdiscs.libs.certificates")
    relocate("org.hamcrest", "io.github.subkek.customdiscs.libs.org.hamcrest")
    relocate("org.junit", "io.github.subkek.customdiscs.libs.org.junit")
    relocate("net.sourceforge.jaad.aac", "io.github.subkek.customdiscs.libs.net.sourceforge.jaad.aac")
    relocate("net.kyori", "io.github.subkek.customdiscs.libs.net.kyori")
    relocate("net.iharder", "io.github.subkek.customdiscs.libs.net.iharder")
    relocate("com.tcoded", "io.github.subkek.customdiscs.libs.com.tcoded")
    relocate("com.grack", "io.github.subkek.customdiscs.libs.com.grack")
    relocate("dev.lavalink", "io.github.subkek.customdiscs.libs.dev.lavalink")
    relocate("org.intellij", "io.github.subkek.customdiscs.libs.org.intellij")
    relocate("org.jetbrains", "io.github.subkek.customdiscs.libs.org.jetbrains")
    relocate("com.sedmelluq", "io.github.subkek.customdiscs.libs.com.sedmelluq") {
        exclude("com/sedmelluq/discord/lavaplayer/natives/**")
    }

    //minimize()
}

tasks.processResources {
    filesMatching("plugin.yml") {
        expand(project.properties)
    }
}
