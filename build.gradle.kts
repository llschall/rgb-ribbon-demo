plugins {
    kotlin("jvm") version "1.9.22"
    id("org.jetbrains.compose") version "1.5.12"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation("io.github.llschall:ardwloop:0.3.3")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

compose.desktop {
    application {
        mainClass = "org.example.MainKt"
    }
}
