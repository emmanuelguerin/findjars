package com.criteo.gradle.findjars

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.*

open class FindJarsPlugin : Plugin<Project> {
    override fun apply(project: Project): Unit = project.run {
        tasks.create<FindJarsTask>("findJars") {
            description = "Find jars in a given configuration."
            group = "debug"
        }
    }
}
