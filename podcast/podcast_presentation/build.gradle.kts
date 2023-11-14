plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

apply(from = "$rootDir/compose-module.gradle")

android {
    namespace = "com.example.podcast_presentation"
}
dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.podcastDomain))
    implementation(Coil.coilCompose)
    api(Paging.pagingRuntime)
    api(Paging.pagingCompose)
}