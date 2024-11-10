// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
}

// build.gradle.kts (nível do projeto)
allprojects {
    repositories {
        google() // Repositório Google para o Hilt
        mavenCentral()
        maven("https://jitpack.io") // para o TarsosDSP
    }
}
