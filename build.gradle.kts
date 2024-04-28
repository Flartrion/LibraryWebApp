repositories {
    mavenCentral()
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