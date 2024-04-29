import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack

repositories {
    mavenCentral()
}

group = "com.flartrion.librapp.backend"
version = "1.1-SNAPSHOT"

plugins {
    kotlin("multiplatform") version "1.9.23"
    kotlin("plugin.serialization") version "1.9.23"
    id("io.ktor.plugin") version "2.3.10"
    application
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "15"
        }
        testRuns["test"].executionTask.configure {
            useJUnit()
        }
        withJava()
    }

    js {
        binaries.executable()
        browser {
            commonWebpackConfig {

            }
        }
    }

    val ktor_version: String by project
    val exposed_version: String by project
    val java_websocket_version: String by project
    val kotlin_react_wrappers_version: String by project
    val hikaricp_version: String by project
    val ehcache_version: String by project

    sourceSets {
        val jvmMain by getting {
            dependencies {
                runtimeOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
                implementation("io.ktor:ktor-server-core:$ktor_version")
                implementation("io.ktor:ktor-server-netty:$ktor_version")
                implementation("io.ktor:ktor-server-html-builder:$ktor_version")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
                implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
                implementation("io.ktor:ktor-server-resources:$ktor_version")
                implementation("io.ktor:ktor-server-status-pages:$ktor_version")
                implementation("io.ktor:ktor-server-config-yaml:$ktor_version")
                implementation("io.ktor:ktor-server-auth:$ktor_version")
                implementation("io.ktor:ktor-server-sessions:$ktor_version")
//                implementation("ch.qos.logback:logback-classic")
//                implementation("org.apache.logging.log4j:log4j-core")
//                implementation("org.apache.logging.log4j:log4j-slf4j-impl")

                implementation("org.postgresql:postgresql:42.7.3")
                implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
                implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
                implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
                implementation("org.jetbrains.exposed:exposed-kotlin-datetime:$exposed_version")

                implementation("com.zaxxer:HikariCP:$hikaricp_version")
                implementation("org.ehcache:ehcache:$ehcache_version")
            }
        }

    }
}


application {
//    mainClass.set("$group.ServerKt")
    mainClass.set("ServerKt")
}

tasks.getByName<Jar>("jvmJar") {
//    dependsOn(tasks.getByName("jsBrowserProductionWebpack"))
//    val jsBrowserProductionWebpack = tasks.getByName<KotlinWebpack>("jsBrowserProductionWebpack")
//    from(jsBrowserProductionWebpack.outputDirectory, jsBrowserProductionWebpack.mainOutputFileName)
}

tasks.register<Copy>("elevateOutputsBack") {
    group = "build"
    from(tasks.getByName<Jar>("jvmJar").destinationDirectory)
    into(project.relativeProjectPath("../out"))
    include("*.jar")
    dependsOn(tasks.getByName<Jar>("jvmJar"))
}

tasks.getByName<JavaExec>("run") {
    dependsOn(tasks.getByName<Jar>("jvmJar"))
    classpath(tasks.getByName<Jar>("jvmJar"))
}