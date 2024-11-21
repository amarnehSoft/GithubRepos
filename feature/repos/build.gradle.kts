plugins {
    alias(libs.plugins.githubRepos.android.feature)
    alias(libs.plugins.githubRepos.android.library.compose)
}

android {
    namespace = "com.github.repos.feature.repos"
}

dependencies {
    implementation(projects.core.data)
}