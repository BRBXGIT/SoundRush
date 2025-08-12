package com.example.common.dispatchers

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val soundRushDispatcher: SoundRushDispatchers)

enum class SoundRushDispatchers {
    Default,
    IO,
    Main
}