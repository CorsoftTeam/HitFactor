plugins {
    alias(libs.plugins.hf.androidLib)
    alias(libs.plugins.hf.compose)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.corsoft.services"
}

ksp {
    arg("compose-destinations.moduleName", "services")
    arg("compose-destinations.mode", "destinatons")
}

dependencies {
    ksp(libs.navigation.ksp)
    implementation(project(path = ":core:ui"))
    implementation(project(path = ":core:resources"))
    implementation(project(":core:common"))
    implementation(project(":core:network"))
    implementation(project(":data:auth"))
    implementation(project(":data:user"))
    implementation(platform(libs.koin.bom))
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.core)
    implementation(libs.bundles.compose)
    implementation(libs.bundles.koin)
    implementation(libs.bundles.compose.navigation)
    implementation(libs.bundles.music)
}