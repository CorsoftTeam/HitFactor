plugins {
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hf.androidLib)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.corsoft.hitfactor.data.ballistic"

    defaultConfig {
        externalNativeBuild {
            cmake {
                cppFlags += "-std=c++11"
            }
        }
    }

    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
        }
    }
}

dependencies {
    ksp(libs.room.ksp)
    implementation(platform(libs.koin.bom))
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.core)
    implementation(libs.bundles.koin)
    implementation(libs.bundles.retrofit)
    implementation(libs.bundles.firebase)
    implementation(libs.bundles.room)
    implementation(project(":core:network"))
    implementation(project(":core:data"))
}