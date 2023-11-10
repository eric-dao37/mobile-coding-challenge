plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

apply(from = "$rootDir/library-module.gradle")
android {
    namespace = "com.example.podcast_data"
}
dependencies {
    implementation(project(Modules.podcastDomain))

    implementation(Retrofit.okHttp)
    implementation(Retrofit.retrofit)
    implementation(Retrofit.okHttpLoggingInterceptor)
    implementation(Retrofit.moshiConverter)
    implementation(Coroutines.coroutines)

}

