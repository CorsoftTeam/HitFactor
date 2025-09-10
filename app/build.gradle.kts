plugins {
    alias(libs.plugins.hf.app)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.corsoft.hitfactor"
}

dependencies {
    implementation(project(":core:provider"))
    ksp(libs.navigation.ksp)
    implementation(platform(libs.compose.bom))
    implementation(platform(libs.koin.bom))
    implementation(platform(libs.rustore.bom))
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)
    implementation(libs.bundles.core)
    implementation(libs.bundles.koin)
    implementation(libs.bundles.compose)
    implementation(libs.bundles.testing)
    implementation(libs.bundles.compose.navigation)
    implementation(libs.bundles.payments)
    implementation(libs.bundles.appmetrica)
    implementation(project(":core:ui"))
    implementation(project(":core:resources"))
    implementation(project(":core:network"))
    implementation(project(":core:data"))
    implementation(project(":core:common"))
    implementation(project(":feature:auth"))
    implementation(project(":feature:services"))
    implementation(project(":feature:payments"))
    implementation(project(":data:auth"))
    implementation(project(":data:user"))
    implementation(project(":data:payments"))
    implementation(project(":data:analytics"))
    implementation(project(":data:ballistic"))
}