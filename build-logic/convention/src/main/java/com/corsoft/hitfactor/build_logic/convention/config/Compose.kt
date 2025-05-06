@file:Suppress("UnstableApiUsage")

package com.corsoft.hitfactor.build_logic.convention.config

import com.android.build.api.dsl.CommonExtension
import com.corsoft.hitfactor.build_logic.convention.ProjectConfig

internal fun configureCompose(commonExtension: CommonExtension<*, *, *, *, *, *>) {
    commonExtension.apply {
        buildFeatures.compose = true

        composeOptions {
            kotlinCompilerExtensionVersion = ProjectConfig.kotlinCompiler
        }
    }
}