plugins {
    alias(libs.plugins.githubRepos.android.library)
    alias(libs.plugins.githubRepos.hilt)
    id("kotlinx-serialization")
}

android {
    namespace = "com.github.repos.core.data"
}

dependencies {
    api(projects.core.common)
//    api(projects.core.database)
//    api(projects.core.datastore)
//    api(projects.core.network)
}
