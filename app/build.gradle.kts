plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("kotlin-kapt")
    id("androidx.navigation.safeargs")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.synergy7ch4"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.synergy7ch4"
        minSdk = 31
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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    implementation(platform(libs.compose.bom))
    implementation(libs.compose.material)
    implementation(libs.compose.ui.preview)
    implementation(libs.compose.ui.tooling)
    implementation(libs.compose.activity)

    implementation(libs.room)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    implementation(libs.validator)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
    implementation(libs.coil)
    implementation(libs.livedatas)
    implementation(libs.viewmodels)
    implementation(libs.legacys)
    implementation(libs.datastore)
    implementation(libs.datastore.core)
    implementation(libs.coroutines)
    implementation(libs.recycleview)
    implementation(libs.preferences)


}