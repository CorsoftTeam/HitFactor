package com.corsoft.hitfactor.build_logic.convention

import org.gradle.api.JavaVersion

@Suppress("ConstPropertyName")
internal object ProjectConfig {
    const val appId = "com.corsoft.hitfactor"
    const val minSdk = 25
    const val compileSdk = 34
    const val targetSdk = 34
    const val versionCode = 1
    const val versionName = "1.0"
    val javaVersion = JavaVersion.VERSION_19
    const val kotlinCompiler = "1.5.1"
}