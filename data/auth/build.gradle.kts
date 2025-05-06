plugins {
    alias(libs.plugins.hf.androidLib)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.corsoft.data.auth"
}

dependencies {
    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.core)
    implementation(libs.bundles.koin)
    implementation(libs.bundles.retrofit)
    implementation(project(":core:network"))
    implementation(project(":core:data"))
}