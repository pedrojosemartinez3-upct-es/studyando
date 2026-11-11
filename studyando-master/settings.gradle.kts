pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        // Versiones de plugins
        id("com.android.application") version "8.12.2"
        id("org.jetbrains.kotlin.android") version "2.0.0"
        id("org.jetbrains.kotlin.plugin.compose") version "2.0.0" // requerido desde Kotlin 2.0
        id("com.google.dagger.hilt.android") version "2.52"
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "studyando"
include(":app")