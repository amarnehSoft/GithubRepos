plugins {
    alias(libs.plugins.githubRepos.android.library)
    alias(libs.plugins.githubRepos.android.room)
    alias(libs.plugins.githubRepos.hilt)
}

android {
    namespace = "com.github.repos.core.database"
}

dependencies {
    api(projects.core.model)
    implementation(libs.kotlinx.datetime)
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.room.paging)
}
