plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}
apply(from = "$rootDir/library-module.gradle")

android {
    namespace = "com.example.podcast_domain"
}

dependencies {
    implementation(project(Modules.core))

    api(Paging.pagingCommon)
}