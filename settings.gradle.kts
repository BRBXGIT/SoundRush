pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "SoundRush"
include(":app")
include("core")
include("feature")
include("feature")

include(":core:network")
include(":core:data")
include(":core:local")
include(":core:design-system")
include(":core:common")
include(":feature:onboarding-screen")
include(":feature:common")
include(":feature:home-screen")
include(":feature:playlist-screen")
