pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "GithupRepos"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
//include(":core:analytics")
include(":core:common")
include(":core:data")
//include(":core:data-test")
//include(":core:database")
//include(":core:datastore")
//include(":core:datastore-proto")
//include(":core:datastore-test")
//include(":core:designsystem")
//include(":core:domain")
//include(":core:model")
//include(":core:network")
//include(":core:notifications")
//include(":core:screenshot-testing")
//include(":core:testing")
//include(":core:ui")
//
//include(":feature:foryou")
//include(":feature:interests")
//include(":feature:bookmarks")
//include(":feature:topic")
//include(":feature:search")
//include(":feature:settings")
//include(":lint")
//include(":sync:work")
//include(":sync:sync-test")
//include(":ui-test-hilt-manifest")