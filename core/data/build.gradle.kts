plugins {
    alias(libs.plugins.hf.androidLib)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.corsoft.data"
}

dependencies {
    ksp(libs.room.ksp)
    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.core)
    implementation(libs.bundles.koin)
    implementation(libs.bundles.room)
    implementation(project(":core:common"))
}