import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

plugins {
    `kotlin-dsl`
    id("com.gradle.plugin-publish") version "0.10.0"
}

repositories {
    mavenCentral()
}

val descriptionText = "Tool for finding jars in a given configuration."
description = descriptionText

dependencies {
    implementation("commons-codec:commons-codec:1.9")
    testImplementation("junit:junit:4.12")
    testImplementation("org.assertj:assertj-core:3.11.1")
}

sourceSets {
    create("functionalTest") {
        withConvention(KotlinSourceSet::class) {
            this.kotlin.srcDir(file ("src/functTest/kotlin"))
        }
        compileClasspath += sourceSets.main.get().output + configurations.testCompileClasspath
        runtimeClasspath += output + compileClasspath
    }
}

task<Test>("functionalTest") {
    description = "Runs the functional tests."
    group = "verification"
    testClassesDirs = sourceSets["functionalTest"].output.classesDirs
    classpath = sourceSets["functionalTest"].runtimeClasspath
}

gradlePlugin {
    plugins {
        register("findJars") {
            id = "com.criteo.gradle.findjars"
            displayName = "FindJars plugin"
            description = descriptionText
            implementationClass = "com.criteo.gradle.findjars.FindJarsPlugin"
        }
    }
    testSourceSets(sourceSets["functionalTest"])
}

pluginBundle {
    website = "https://github.com/criteo/findjars"
    vcsUrl = "https://github.com/criteo/findjars.git"
    description = descriptionText
    tags = mutableListOf("find", "jar", "debug", "classpath", "conflicts")
}
