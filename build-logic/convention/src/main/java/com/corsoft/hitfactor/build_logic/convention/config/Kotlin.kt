package com.corsoft.hitfactor.build_logic.convention.config

import com.corsoft.hitfactor.build_logic.convention.ProjectConfig
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureKotlin() {
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = ProjectConfig.javaVersion.toString()
        }
    }
}