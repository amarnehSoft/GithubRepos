plugins {
    alias(libs.plugins.githubRepos.android.feature)
    alias(libs.plugins.githubRepos.android.library.compose)
}

android {
    namespace = "com.github.repos.feature.repos"
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.feature.details)

    implementation(libs.androidx.compose.material3.adaptive)
    implementation(libs.androidx.compose.material3.adaptive.layout)
    implementation(libs.androidx.compose.material3.adaptive.navigation)
}