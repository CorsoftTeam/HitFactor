plugins {
    alias(libs.plugins.hf.androidLib)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.corsoft.hitfactor.data.payments"
}

dependencies {
    implementation(platform(libs.koin.bom))
    implementation(platform(libs.rustore.bom))
    implementation(libs.bundles.core)
    implementation(libs.bundles.koin)
    implementation(libs.bundles.retrofit)
    implementation(libs.bundles.payments)
    implementation(project(":core:network"))
    implementation(project(":core:data"))
}