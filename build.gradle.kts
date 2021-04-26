import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.32"
    kotlin("plugin.serialization") version "1.4.32"
    application
}

group = "me.vladimir"
version = "1.0-SNAPSHOT"

// Это нужно для сериализации Котлина (сканируется аннотация Serializable)
buildscript {
    repositories { mavenCentral() }

    dependencies {
        val kotlinVersion = "1.4.32"
        classpath(kotlin("gradle-plugin", version = kotlinVersion))
        classpath(kotlin("serialization", version = kotlinVersion))
    }
}


repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.microutils:kotlin-logging:2.0.6")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json-jvm:1.1.0")

    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "13"
}

application {
    mainClassName = "MainKt"
}