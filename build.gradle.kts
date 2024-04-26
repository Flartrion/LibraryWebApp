import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack

plugins {
    kotlin("multiplatform") version "1.9.23"
    application
}

group = "me.user"
version = "1.1-SNAPSHOT"

repositories {
//    jcenter()
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/kotlin/p/kotlin/kotlin-js-wrappers") }
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

    js(IR) {
        binaries.executable()
        browser {
            commonWebpackConfig {

            }
        }
    }
    val ktor_version: String by project
    val exposed_version: String by project
    val h2_version: String by project
    val java_websocket_version: String by project
    val kotlin_react_wrappers_version: String by project

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("io.ktor:ktor-server-core:$ktor_version")
                implementation("io.ktor:ktor-server-netty:$ktor_version")
                implementation("io.ktor:ktor-server-html-builder:$ktor_version")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
                implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
                implementation("io.ktor:ktor-server-locations:$ktor_version")
                implementation("io.ktor:ktor-server-config-yaml:$ktor_version")
                implementation("io.ktor:ktor-server-auth:$ktor_version")
                implementation("ch.qos.logback:logback-classic")
                implementation("org.java-websocket:Java-WebSocket:$java_websocket_version")
                implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
                implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
                implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
                implementation("com.h2database:h2:$h2_version")
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-core:$kotlin_react_wrappers_version")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react:$kotlin_react_wrappers_version")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:$kotlin_react_wrappers_version")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-emotion:11.11.4-pre.733")

                implementation(npm("react", "17.0.2"))
                implementation(npm("react-dom", "17.0.2"))
                implementation(npm("styled-components", "~5.3.0"))
            }
        }
    }
}

application {
//    mainClass.set("io.ktor.server.netty.EngineMain")
    application {
        mainClass.set("$group.ServerKt")
    }
}

tasks.getByName<KotlinWebpack>("jsBrowserProductionWebpack") {
    mainOutputFileName = "js.js"
}

tasks.getByName<Jar>("jvmJar") {
    dependsOn(tasks.getByName("jsBrowserProductionWebpack"))
    val jsBrowserProductionWebpack = tasks.getByName<KotlinWebpack>("jsBrowserProductionWebpack")
    from(jsBrowserProductionWebpack.outputDirectory, jsBrowserProductionWebpack.mainOutputFileName)
}

tasks.getByName<JavaExec>("run") {
    dependsOn(tasks.getByName<Jar>("jvmJar"))
    classpath(tasks.getByName<Jar>("jvmJar"))
}