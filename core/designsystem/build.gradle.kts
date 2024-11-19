plugins {
    alias(libs.plugins.githubRepos.android.library)
    alias(libs.plugins.githubRepos.android.library.compose)
}

android {
    namespace = "com.github.repos.core.designsystem"
}

dependencies {
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.compose.material.iconsExtended)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.material3.adaptive)
    api(libs.androidx.compose.material3.navigationSuite)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.ui.util)
    implementation(libs.coil.kt.compose)
}
