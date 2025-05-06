pluginManagement {
    repositories {
        includeBuild("build-logic")
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "HitFactor"
include(":app")
include(":core")
include(":core:data")
include(":core:ui")
include(":core:resources")
include(":feature")
include(":core:common")
include(":feature:auth")
include(":core:network")
include(":data")
include(":data:auth")
include(":feature:services")
include(":core:provider")
include(":data:user")
