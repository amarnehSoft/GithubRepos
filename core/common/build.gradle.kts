plugins {
    alias(libs.plugins.githubRepos.jvm.library)
    alias(libs.plugins.githubRepos.hilt)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
}