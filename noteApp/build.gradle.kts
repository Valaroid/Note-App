plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.jetbrainsKotlinKsp)
    alias(libs.plugins.hiltPlugin)
    id("kotlin-parcelize")

}

android {
    namespace = "com.example.mvp2projects"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.mvp2projects"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures{
        viewBinding=true
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
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

    //room
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    ksp(libs.androidx.room.compiler)
    //for link with rxJava3
    implementation (libs.androidx.room.rxjava3 )


    //hilt
    implementation (libs.hilt.android)
    ksp(libs.dagger.compiler)
    ksp(libs.hilt.compiler)


    //rxJava3
    implementation (libs.rxandroid)
    implementation (libs.rxjava)
}