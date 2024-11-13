plugins {
    //alias(libs.plugins.android.application)
    //alias(libs.plugins.google.gms.google.services)
    // Add the dependency for the Google services Gradle plugin
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.Plantze.tracker"
    compileSdk = 34
    testOptions {
        unitTests.isReturnDefaultValues = true
    }
    defaultConfig {
        applicationId = "com.Plantze.tracker"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.database)
    implementation(libs.firebase.storage)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("com.fasterxml.jackson.core:jackson-databind:2.12.3")
    implementation(platform("com.google.firebase:firebase-bom:33.5.1"))
    implementation("com.google.firebase:firebase-analytics")
}
