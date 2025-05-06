package com.corsoft.hitfactor.build_logic.convention.config

import com.android.build.api.dsl.LibraryExtension
import com.corsoft.hitfactor.build_logic.convention.ProjectConfig
import org.gradle.api.Project

internal fun Project.configureAndroidLibrary(libExtension: LibraryExtension) {
    libExtension.apply {
        compileSdk = ProjectConfig.compileSdk
        defaultConfig {
            minSdk = ProjectConfig.minSdk
        }

        compileOptions {
            sourceCompatibility = ProjectConfig.javaVersion
            targetCompatibility = ProjectConfig.javaVersion
        }
        configureKotlin()

        buildFeatures {
            buildConfig = true
        }

        configureFlavors(this)
    }
}