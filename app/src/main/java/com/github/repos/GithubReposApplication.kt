package com.github.repos

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * [Application] class for Github Repos
 */
@HiltAndroidApp
class GithubReposApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}
