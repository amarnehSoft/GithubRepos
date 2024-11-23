plugins {
    alias(libs.plugins.githubRepos.android.feature)
    alias(libs.plugins.githubRepos.android.library.compose)
}

android {
    namespace = "com.github.repos.feature.favourites"
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.feature.repos)

    implementation(libs.androidx.paging.runtime)
}