import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.32"
    kotlin("plugin.serialization") version "1.4.32"
    application
}

group = "me.vladimir"

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

    implementation ("org.deeplearning4j:deeplearning4j-core:1.0.0-beta7") {
        exclude("org.bytedeco", "openblas")
    }
    implementation ("org.nd4j:nd4j-native-platform:1.0.0-beta7")


    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.0")
    testImplementation("org.assertj:assertj-core:3.19.0")
    testImplementation("io.mockk:mockk:1.11.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}

application {
    mainClassName = "MainKt"
}

tasks.withType<Tar> {
    enabled = false
}

tasks.withType<Jar> {
    enabled = true
    manifest {
        attributes(
            "Main-Class" to "MainKt",
            "Start-Class" to "MainKt"
        )
    }
    archiveBaseName.set("prog")
}