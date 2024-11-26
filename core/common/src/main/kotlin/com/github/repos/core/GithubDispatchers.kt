package com.github.repos.core

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val dispatcher: GithubDispatchers)

enum class GithubDispatchers {
    Default,
    IO,
}
