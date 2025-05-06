plugins {
    alias(libs.plugins.hf.androidLib)
}

android {
    namespace = "com.vaskorr.provider"
}

dependencies {

    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.core)
    implementation(project(":core:resources"))
}