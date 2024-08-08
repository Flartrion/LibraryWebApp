import com.github.gradle.node.npm.task.NpmTask

repositories {
    mavenCentral()
}

plugins {
    id("com.github.node-gradle.node") version "3.1.1"
    base
}

group = "com.flartrion.librapp.frontend"

node {
//    version = "20.12.2"
    download = true
}


tasks.register<NpmTask>("jsStatic") {
    group = "build"
    args = listOf("run", "build")
    dependsOn(tasks.named("cleanPreviousFront"))
}

tasks.register<Delete>("cleanPreviousFront") {
    group = "cleanup"

    val jsRegex = Regex("(.*)(?>\\.js)(?>\\.map){0,1}")

//    outputs.upToDateWhen { false }
//    setOnlyIf { true }

    doFirst {
        delete(File(project.projectDir.toString() + ("/out")).listFiles { it ->
            jsRegex.matches(it.name)
        })
    }
}


