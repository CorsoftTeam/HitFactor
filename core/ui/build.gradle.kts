plugins {
    alias(libs.plugins.hf.androidLib)
    alias(libs.plugins.hf.compose)
}

android {
    namespace = "com.corsoft.ui"
}

dependencies {

    implementation(project(path = ":core:resources"))
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.core)
    implementation(libs.bundles.compose)
}