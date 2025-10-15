plugins {
    kotlin("jvm") version "2.1.21"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation("io.github.llschall:ardwloop:0.3.3")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
application {
    mainClass.set("MainKt")
}
