plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.corsoft.hitfactor"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.corsoft.hitfactor"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
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
}