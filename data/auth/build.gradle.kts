plugins {
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hf.androidLib)
}

android {
    namespace = "com.corsoft.data.auth"
}

dependencies {
    implementation(platform(libs.koin.bom))
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)
    implementation(libs.bundles.core)
    implementation(libs.bundles.koin)
    implementation(libs.bundles.retrofit)
    implementation(project(":core:network"))
    implementation(project(":core:data"))
}