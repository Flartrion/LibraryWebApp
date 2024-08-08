import org.jetbrains.kotlin.gradle.dsl.JvmTarget

repositories {
    mavenCentral()
    gradlePluginPortal()
}

group = "com.flartrion.librapp.backend"
version = "1.1-SNAPSHOT"

plugins {
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.serialization") version "1.9.23"
    id("io.ktor.plugin") version "2.3.10"
    `java-base`
    application
}



kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_1_8)
    }

    val ktor_version: String by project
    val exposed_version: String by project
    val java_websocket_version: String by project
    val kotlin_react_wrappers_version: String by project
    val hikaricp_version: String by project
    val ehcache_version: String by project

    sourceSets.main {
        kotlin.srcDirs("src/main/kotlin")
        resources.srcDirs("src/main/resources")
    }

    sourceSets {
        val main by getting {
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
                implementation("io.ktor:ktor-server-auth-jwt:$ktor_version")
//                implementation("org.apache.logging.log4j:log4j-core:2.23.1")
//                implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.23.1")

                implementation("org.postgresql:postgresql:42.7.3")
                implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
                implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
                implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
                implementation("org.jetbrains.exposed:exposed-kotlin-datetime:$exposed_version")

                implementation("io.ktor:ktor-client-core:$ktor_version")
                implementation("io.ktor:ktor-client-cio:$ktor_version")
                implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")

                implementation("com.zaxxer:HikariCP:$hikaricp_version")
                implementation("org.ehcache:ehcache:$ehcache_version")
            }
        }

    }
}

dependencies {

}

application {
    mainClass.set("ServerKt")
}

ktor {
    fatJar {

    }
}


tasks.withType<JavaCompile> {
    targetCompatibility = "1.8"
    sourceCompatibility = "1.8"
}

tasks.register<Delete>("cleanPreviousBack") {
    group = "cleanup"

    val jsRegex = Regex("(.*)(?>\\.js)(?>\\.map){0,1}")


    outputs.upToDateWhen { false }
    setOnlyIf { true }

    doFirst {
        delete(File(project.projectDir.toString() + ("/src/main/resources/static")).listFiles { it ->
            jsRegex.matches(it.name)
        })
    }
}



tasks.getByName<Jar>("jar") {
//    dependsOn(tasks.getByName("jsBrowserProductionWebpack"))
//    val jsBrowserProductionWebpack = tasks.getByName<KotlinWebpack>("jsBrowserProductionWebpack")
//    from(jsBrowserProductionWebpack.outputDirectory, jsBrowserProductionWebpack.mainOutputFileName)
}

tasks.getByName<Copy>("processResources") {
    duplicatesStrategy = DuplicatesStrategy.WARN
    dependsOn(parent!!.tasks.named("elevateOutputsFront"))
}

tasks.register<Copy>("elevateOutputsBack") {
    group = "build"
    dependsOn(tasks.getByName("buildFatJar"))
    duplicatesStrategy = DuplicatesStrategy.WARN
    from(tasks.getByName<Jar>("jar").destinationDirectory)
//    from("src/jvmMain/resources")
    into(project.relativeProjectPath("../out"))
    include("*.jar")
}

tasks.getByName<JavaExec>("run") {
    dependsOn(tasks.getByName<Jar>("jar"))
    classpath(tasks.getByName<Jar>("jar"))
}