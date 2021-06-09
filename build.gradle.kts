import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack

plugins {
    kotlin("multiplatform") version "1.5.0"
    kotlin("plugin.serialization") version "1.5.0"
    application
}

group = "me.user"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
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
    js(LEGACY) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.1")
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("io.ktor:ktor-serialization:1.5.4")
                implementation("io.ktor:ktor-server-core:1.5.4")
                implementation("io.ktor:ktor-server-netty:1.5.4")
                implementation("io.ktor:ktor-auth:1.5.4")
                implementation("io.ktor:ktor-html-builder:1.5.4")
                implementation("ch.qos.logback:logback-classic:1.2.3")
                implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.2")
                implementation("org.postgresql:postgresql:42.2.20")
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))
                implementation("org.jetbrains:kotlin-react:17.0.1-pre.148-kotlin-1.4.30")
                implementation("org.jetbrains:kotlin-react-dom:17.0.1-pre.148-kotlin-1.4.30")
                implementation(npm("react", "17.0.2"))
                implementation(npm("react-dom", "17.0.2"))
                implementation("org.jetbrains:kotlin-styled:5.2.1-pre.148-kotlin-1.4.30")
                implementation(npm("styled-components", "~5.3.0"))
            }
        }
    }
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

tasks.getByName<KotlinWebpack>("jsBrowserProductionWebpack") {
    outputFileName = "js.js"
}

tasks.getByName<Jar>("jvmJar") {
    dependsOn(tasks.getByName("jsBrowserProductionWebpack"))
    val jsBrowserProductionWebpack = tasks.getByName<KotlinWebpack>("jsBrowserProductionWebpack")
    from(File(jsBrowserProductionWebpack.destinationDirectory, jsBrowserProductionWebpack.outputFileName))
}

tasks.getByName<JavaExec>("run") {
    dependsOn(tasks.getByName<Jar>("jvmJar"))
    classpath(tasks.getByName<Jar>("jvmJar"))
}