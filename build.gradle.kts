repositories {
    mavenCentral()
}

dependencies {
}

group = "com.flartrion.librapp"
version = "1.1-SNAPSHOT"

plugins {

}
//tasks.getByName<Jar>("jvmJar") {
////    dependsOn(tasks.getByName("jsBrowserProductionWebpack"))
////    val jsBrowserProductionWebpack = tasks.getByName<KotlinWebpack>("jsBrowserProductionWebpack")
////    from(jsBrowserProductionWebpack.outputDirectory, jsBrowserProductionWebpack.mainOutputFileName)
//}
//tasks.getByName<JavaExec>("run") {
//    dependsOn(tasks.getByName<Jar>("jvmJar"))
//    classpath(tasks.getByName<Jar>("jvmJar"))
//}

tasks.register<DefaultTask>("prebuildClean") {
    group = "cleanup"
    dependsOn(childProjects["frontend"]!!.tasks.named("cleanPreviousFront"))
    dependsOn(childProjects["backend"]!!.tasks.named("cleanPreviousBack"))
}


tasks.register<Copy>("elevateOutputsFront") {
    group = "build"
    duplicatesStrategy = DuplicatesStrategy.WARN
    dependsOn(tasks.named("prebuildClean"))
    dependsOn(childProjects["frontend"]!!.tasks.named("jsStatic"))
    from(childProjects["frontend"]!!.projectDir.toString() + "/out/") {
//        include("index*")
        include("*.js*")
    }
    from(childProjects["frontend"]!!.projectDir.toString()) {
        include("index*")
    }
//    into(project.relativeProjectPath("../out/static"))
    // Not elegant at all, but whatever, it does the job of throwing static files to server compilation.
    into(childProjects["backend"]!!.projectDir.toString() + "/src/main/resources/static")
}

tasks.register<DefaultTask>("buildFront") {
    group = "build"
    dependsOn(tasks.named("prebuildClean"))
    dependsOn(tasks.named("elevateOutputsFront"))
}

tasks.register<DefaultTask>("buildBack") {
    group = "build"
    dependsOn(project.tasks.named("prebuildClean"))
    dependsOn(project.tasks.named("buildFront"))
    dependsOn(childProjects["backend"]!!.tasks.named("elevateOutputsBack"))
}

tasks.register<GradleBuild>("buildAll") {
    group = "build"
    dependsOn(project.tasks.named("buildFront"))
    dependsOn(project.tasks.named("buildBack"))
}

tasks.register<JavaExec>("devRun") {
    group = "application"
    dependsOn(childProjects["backend"]!!.tasks.named("elevateOutputsFront"))
    dependsOn(childProjects["backend"]!!.tasks.named("elevateOutputsBack"))
    classpath("out/backend-all.jar")
}