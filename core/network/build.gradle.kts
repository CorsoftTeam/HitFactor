plugins {
    alias(libs.plugins.hf.androidLib)
}

android {
    namespace = "com.corsoft.network"
}

dependencies {
    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.core)
    implementation(libs.bundles.koin)
    implementation(libs.bundles.retrofit)
    implementation(project(":core:data"))
    implementation(project(":core:provider"))
}