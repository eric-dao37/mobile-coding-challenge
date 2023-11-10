plugins {
    id ("com.android.application") version "8.0.1" apply false
    id ("com.android.library") version "8.0.1" apply false
    id ("org.jetbrains.kotlin.android") version "1.8.0" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
    id("org.jetbrains.kotlin.jvm") version "1.8.10" apply false
}
//
//buildscript {
//    repositories {
//        google()
//        mavenCentral()
//    }
////    dependencies {
////        classpath(Build.androidBuildTools)
////        classpath(Build.hiltAndroidGradlePlugin)
////        classpath(Build.kotlinGradlePlugin)
////
////        // NOTE: Do not place your application dependencies here; they belong
////        // in the individual module build.gradle files
////    }
//}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}