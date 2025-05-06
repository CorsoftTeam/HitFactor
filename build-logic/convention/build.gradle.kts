plugins {
    `kotlin-dsl`
}

group = "com.corsoft.hitfactor.build_logic"

dependencies {
    compileOnly(libs.android.tools.build.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("appPlugin") {
            id = "hf.app"
            implementationClass = "AppPlugin"
        }
        register("androidLibPlugin") {
            id = "hf.androidLib"
            implementationClass = "AndroidLibraryPlugin"
        }
        register("composePlugin") {
            id = "hf.compose"
            implementationClass = "ComposePlugin"
        }
    }
}