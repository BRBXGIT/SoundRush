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
include(":core:design-system")
include(":core:data")
include(":core:network")
include(":core:local")
include("feature")
include(":feature:navbar-screens")
include(":feature:composition-screen")
include(":core:common")
include(":feature:playlist-screen")
include(":feature:onboarding-screen")
