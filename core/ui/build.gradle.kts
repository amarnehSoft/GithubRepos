plugins {
    alias(libs.plugins.githubRepos.android.library)
    alias(libs.plugins.githubRepos.android.library.compose)
}

android {
    namespace = "com.github.repos.core.ui"
}

dependencies {
    api(libs.androidx.metrics)
    api(projects.core.designsystem)
    api(projects.core.model)

    implementation(libs.androidx.browser)
    implementation(libs.coil.kt)
    implementation(libs.coil.kt.compose)
}
