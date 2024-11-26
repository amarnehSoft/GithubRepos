plugins {
    alias(libs.plugins.githubRepos.android.library)
    alias(libs.plugins.githubRepos.android.library.compose)
}

android {
    namespace = "com.github.repos.core.ui"
}

dependencies {
    api(projects.core.designsystem)
    api(projects.core.model)

    implementation(libs.coil.kt.compose)
    implementation(libs.androidx.paging.compose)
}
