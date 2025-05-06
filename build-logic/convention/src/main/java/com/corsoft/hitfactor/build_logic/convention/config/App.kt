package com.corsoft.hitfactor.build_logic.convention.config

import com.android.build.api.dsl.ApplicationExtension
import com.corsoft.hitfactor.build_logic.convention.ProjectConfig
import org.gradle.api.Project

internal fun Project.configureApp(appExtension: ApplicationExtension) {

    fun getPropertiesKey(key: String): String {
        val items = HashMap<String, String>()
        val fl = project.file("keystore.properties")
        (fl.exists()).let {
            fl.forEachLine {
                items[it.split("=")[0]] = it.split("=")[1]
            }
        }
        return items[key]!!
    }

    appExtension.apply {
        compileSdk = ProjectConfig.compileSdk
        defaultConfig {
            applicationId = ProjectConfig.appId
            minSdk = ProjectConfig.minSdk
            targetSdk = ProjectConfig.targetSdk
            versionCode = 1
            versionName = "1.0.0"

            val shop =
                if (project.hasProperty("shopName"))
                    "-" + project.properties["shopName"].toString()
                else
                    ""

            setProperty(
                "archivesBaseName",
                "hf$shop-$versionName[$versionCode]" + project.properties["buildBranch"].toString()
            )

            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

            vectorDrawables {
                useSupportLibrary = true
            }
        }

        compileOptions {
            sourceCompatibility = ProjectConfig.javaVersion
            targetCompatibility = ProjectConfig.javaVersion
        }

        configureKotlin()

        buildFeatures {
            buildConfig = true
        }

        configureCompose(this)

        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
        signingConfigs {
            register("release") {
                storeFile = file(getPropertiesKey("store_file"))
                storePassword = getPropertiesKey("store_password")
                keyAlias = getPropertiesKey("key_alias")
                keyPassword = getPropertiesKey("key_password")
            }
        }

        buildTypes {
            release {
                isDebuggable = false
                isMinifyEnabled = false
                signingConfig = signingConfigs.getByName("release")
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
            debug {
                versionNameSuffix = "-debug"
                isDebuggable = true
                isMinifyEnabled = false
            }
        }

        configureFlavors(this)
    }
}
