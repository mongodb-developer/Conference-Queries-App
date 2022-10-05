plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.mongodb.dublinmug_kmm.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "com.mongodb.dublinmug_kmm.android"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.0"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation(project(":shared"))
    implementation("androidx.compose.ui:ui:1.2.1")
    implementation("androidx.compose.ui:ui-tooling:1.2.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.2.1")
    implementation("androidx.compose.foundation:foundation:1.2.1")

    // Compose 
    // Integration with activities
    implementation("androidx.activity:activity-compose:1.6.0")
    // Compose Material Design
    implementation("androidx.compose.material3:material3:1.0.0-beta03")
    // Animations
    implementation("androidx.compose.animation:animation:1.2.1")
    // Tooling support (Previews, etc.)
    implementation("androidx.compose.ui:ui-tooling:1.2.1")
    // Integration with ViewModels
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")
    implementation ("androidx.compose.runtime:runtime-livedata:1.2.1")
    // UI Tests
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.2.1")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0-native-mt") {
        version {
            strictly("1.6.0-native-mt")
        }
    }

    compileOnly("io.realm.kotlin:library-sync:1.1.0")




}
