plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

apply(from = "$rootDir/compose-module.gradle")

android {
    namespace = "com.example.core"
}
dependencies {
    implementation("androidx.compose.material3:material3:1.1.2")
}
