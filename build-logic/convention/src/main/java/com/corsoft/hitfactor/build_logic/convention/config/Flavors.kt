package com.corsoft.hitfactor.build_logic.convention.config

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.dsl.VariantDimension

private const val DEFAULT_DIMENSION = "default"

private const val PROD = "appProd"
private const val TEST = "appTest"

fun configureFlavors(appExtension: ApplicationExtension) {
    appExtension.apply {
        flavorDimensions += DEFAULT_DIMENSION
        productFlavors {
            val appName = "ППК"
            create(TEST) {
                dimension = DEFAULT_DIMENSION
                resValue("string", "app_name", "$appName (test)")
                applicationIdSuffix = ".test"

                addTestConfigs()
            }
            create(PROD) {
                dimension = DEFAULT_DIMENSION
                resValue("string", "app_name", appName)

                addProdConfigs()
            }
        }
    }
}

fun configureFlavors(libExtension: LibraryExtension) {
    libExtension.apply {
        flavorDimensions += DEFAULT_DIMENSION
        productFlavors {
            create(TEST) {
                dimension = DEFAULT_DIMENSION

                addTestConfigs()
            }
            create(PROD) {
                dimension = DEFAULT_DIMENSION

                addProdConfigs()
            }
        }
    }
}

private fun VariantDimension.addProdConfigs() {
    buildConfigField("String", "BASE_URL", "\"http://176.119.158.108:3002/api/\"")
}

private fun VariantDimension.addTestConfigs() {
    buildConfigField("String", "BASE_URL", "\"http://176.119.158.108:3002/api/\"")
}