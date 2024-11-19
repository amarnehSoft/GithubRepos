plugins {
    alias(libs.plugins.githubRepos.android.library)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.github.repos.core.domain"
}

dependencies {
    api(projects.core.data)
    api(projects.core.model)
    implementation(libs.javax.inject)
}