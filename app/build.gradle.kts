plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.nmq.foodninjaver2"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.nmq.foodninjaver2"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    implementation("com.github.bumptech.glide:glide:4.12.0")
    implementation("androidx.recyclerview:recyclerview:1.3.1")
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")
    implementation("com.squareup.picasso:picasso:2.8")

    // Sử dụng Picasso từ libs
    testImplementation(libs.junit)

    implementation("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")

    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Camera
    implementation(libs.camera.core)
    implementation("androidx.camera:camera-lifecycle:1.4.0")
    implementation("androidx.camera:camera-view:1.4.0")
    implementation("androidx.camera:camera-extensions:1.4.0")
    implementation("androidx.camera:camera-video:1.4.0")
}