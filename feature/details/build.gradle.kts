plugins {
    alias(libs.plugins.githubRepos.android.feature)
    alias(libs.plugins.githubRepos.android.library.compose)
}

android {
    namespace = "com.github.repos.feature.details"
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(libs.coil.kt.compose)
}