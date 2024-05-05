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
}

tasks.register<Copy>("elevateOutputsFront") {
    group = "build"
    duplicatesStrategy = DuplicatesStrategy.WARN
    dependsOn(tasks.getByName("jsStatic"))
    from("out/index.js","out/index.html","out/index.js.map")
//    into(project.relativeProjectPath("../out/static"))
    // Not elegant at all, but whatever, it does the job of throwing static files to server compilation.
    into(project.relativeProjectPath("../backend/src/main/resources/static"))
}
