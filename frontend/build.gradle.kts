import com.moowork.gradle.node.npm.NpmTask
import com.moowork.gradle.node.task.NodeTask

repositories {
    mavenCentral()
}

plugins {
    id("com.moowork.node") version "1.3.1"
}

group = "com.flartrion.librapp"
version = "1.1-SNAPSHOT"


//sourceSets {
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
//}

//tasks.getByName<KotlinWebpack>("jsBrowserProductionWebpack") {
//    mainOutputFileName = "user.js"
//}

task<NodeTask>("buildReactApp") {
    dependsOn(tasks.getByName("npmInstall"))
    setScript(project.file("node_modules/webpack/bin/webpack.js"))
    setArgs(
        listOf(
            "--mode", "development",
            "--entry", "jsMain/js/main.jsx",
            "-o", "/static/"
        )
    )
}

task<NpmTask>("appNpmBuild") {
    setArgs(
        listOf("run", "build")
    )
}

node {
    version = "20.12.2"
    download = true
}



dependencies {

}