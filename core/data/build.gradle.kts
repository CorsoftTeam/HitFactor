plugins {
    alias(libs.plugins.hf.androidLib)
}

android {
    namespace = "com.corsoft.data"
}

dependencies {
    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.core)
    implementation(libs.bundles.koin)
    implementation(project(":core:common"))
}