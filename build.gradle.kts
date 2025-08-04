// This is the top-level build.gradle.kts file in your project's root directory.

plugins {
    // The "apply false" is the critical part that needs to be added.
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
}
