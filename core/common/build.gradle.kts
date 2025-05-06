plugins {
    alias(libs.plugins.hf.androidLib)
}

android {
    namespace = "com.corsoft.common"
}

dependencies {
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.core)
    implementation(libs.bundles.compose)
}