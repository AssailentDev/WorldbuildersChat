import sun.tools.jar.resources.jar
import java.util.Properties

plugins {
    id("java")
}

group = "me.assailent"
version = "1.0.2"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    compileOnly(files("/libs/HytaleServer.jar"))
    compileOnly("net.luckperms:api:5.5")
    compileOnly("com.github.Zoltus:TinyMessage:2.0.1")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks {
    register<Copy>("copyToServer") {
        val path = "C:\\Users\\CRIBG\\Programming\\Hytale Related\\Servers\\TestNow\\Server\\mods"
        from(jar)
        destinationDir = File(path)
    }
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    from("src/main/resources")
}

tasks.test {
    useJUnitPlatform()
}