plugins {
    application
    kotlin("jvm") version "1.3.71"
    id("com.justai.jaicf.jaicp-build-plugin") version "0.1.1"
//    kotlin("jvm") version "1.4.10" // or kotlin("multiplatform") or any other kotlin plugin
//    kotlin("plugin.serialization") version "1.3.71"

}

group = "com.justai.jaicf"
version = "1.0.0"

val jaicf = "0.8.2"
val logback = "1.2.3"

// Main class to run application on heroku. Either JaicpPollerKt, or JaicpServerKt. Will propagate to .jar main class.
application {
    mainClassName = "com.justai.jaicf.template.connections.JaicpServerKt"
}

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
    maven("https://jitpack.io")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation("ch.qos.logback:logback-classic:$logback")

    implementation("com.justai.jaicf:core:$jaicf")
    implementation("com.justai.jaicf:jaicp:$jaicf")
    implementation("com.justai.jaicf:caila:$jaicf")

    implementation("com.beust:klaxon:5.0.1")
//    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
}


tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

tasks.create("stage") {
    dependsOn("shadowJar")
}

tasks.withType<com.justai.jaicf.plugins.jaicp.build.JaicpBuild> {
    mainClassName.set(application.mainClassName)
}