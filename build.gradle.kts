import com.moowork.gradle.node.task.NodeTask
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack

repositories {
    mavenCentral()
}

group = "com.flartrion.librapp"
version = "1.1-SNAPSHOT"

plugins {
    kotlin("multiplatform") version "1.9.23"
    kotlin("plugin.serialization") version "1.9.23"
    id("io.ktor.plugin") version "2.3.10"
    id("com.moowork.node") version "1.3.1"
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

//    js {
//        binaries.executable()
//        browser {
//            commonWebpackConfig {
//                cssSupport {
//                    enabled.set(true)
//                    mode.set("extract")
//                }
//            }
//        }
//    }

    val ktor_version: String by project
    val exposed_version: String by project
    val java_websocket_version: String by project
    val kotlin_react_wrappers_version: String by project
    val hikaricp_version: String by project
    val ehcache_version: String by project

    sourceSets {
        val commonMain by getting {
            dependencies {

            }
        }
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
//        val jsMain by getting {
//            dependencies {
//                implementation(kotlin("stdlib"))
//                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-core:$kotlin_react_wrappers_version")
//                implementation("org.jetbrains.kotlin-wrappers:kotlin-react:$kotlin_react_wrappers_version")
//                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:$kotlin_react_wrappers_version")
//                implementation("org.jetbrains.kotlin-wrappers:kotlin-emotion:11.11.4-pre.733")
//
//                implementation(npm("react", "17.0.2"))
//                implementation(npm("react-dom", "17.0.2"))
//                implementation(npm("styled-components", "~5.3.0"))
//            }
//        }
    }
}


application {
//    mainClass.set("$group.ServerKt")
    mainClass.set("ServerKt")
}


//task<NodeTask>("buildReactApp") {
//    dependsOn(tasks.getByName("npmInstall"))
//    setScript(project.file("node_modules/webpack/bin/webpack.js"))
//    setArgs(
//        listOf(
//            "--mode", "development",
//            "--entry", "jsMain/js/main.jsx",
//            "-o", "/static/"
//        )
//    )
//}
//
//node {
//    version = "20.12.2"
//    download = true
//}

//tasks.getByName<KotlinWebpack>("jsBrowserProductionWebpack") {
//    mainOutputFileName = "user.js"
//}

tasks.getByName<Jar>("jvmJar") {
//    dependsOn(tasks.getByName("jsBrowserProductionWebpack"))
//    val jsBrowserProductionWebpack = tasks.getByName<KotlinWebpack>("jsBrowserProductionWebpack")
//    from(jsBrowserProductionWebpack.outputDirectory, jsBrowserProductionWebpack.mainOutputFileName)
}
tasks.getByName<JavaExec>("run") {
    dependsOn(tasks.getByName<Jar>("jvmJar"))
    classpath(tasks.getByName<Jar>("jvmJar"))
}