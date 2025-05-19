plugins {
    alias(libs.plugins.hf.androidLib)
    alias(libs.plugins.hf.compose)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.corsoft.hitfactor.feature.payments"
}

dependencies {
    ksp(libs.navigation.ksp)
    implementation(project(path = ":core:ui"))
    implementation(project(path = ":core:resources"))
    implementation(project(":core:common"))
    implementation(project(":core:network"))
    implementation(project(":data:payments"))
    implementation(platform(libs.koin.bom))
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.core)
    implementation(libs.bundles.compose)
    implementation(libs.bundles.koin)
    implementation(libs.bundles.compose.navigation)
}