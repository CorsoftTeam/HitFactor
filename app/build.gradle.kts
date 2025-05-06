plugins {
    alias(libs.plugins.hf.app)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.corsoft.hitfactor"
}

dependencies {
    implementation(project(":core:provider"))
    ksp(libs.navigation.ksp)
    implementation(platform(libs.compose.bom))
    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.core)
    implementation(libs.bundles.koin)
    implementation(libs.bundles.compose)
    implementation(libs.bundles.testing)
    implementation(libs.bundles.compose.navigation)
    implementation(project(":core:ui"))
    implementation(project(":core:resources"))
    implementation(project(":core:network"))
    implementation(project(":core:data"))
    implementation(project(":core:common"))
    implementation(project(":feature:auth"))
    implementation(project(":feature:services"))
    implementation(project(":data:auth"))
    implementation(project(":data:user"))
}