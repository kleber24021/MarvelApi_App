import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.kotlinAndroidKsp)
    alias(libs.plugins.hiltAndroid)
}

android {
    namespace = "com.drewsoft.marvelapiapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.drewsoft.marvelapiapp"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        val keystorefile = project.rootProject.file("apikey.properties")
        val properties = Properties()
        properties.load(keystorefile.inputStream())

        val publicKey = properties.getProperty("MARVEL_PUBLIC_API_KEY") ?: ""
        val privateKey = properties.getProperty("MARVEL_PRIVATE_API_KEY") ?: ""


        buildConfigField("String", "PUBLIC_API_KEY", publicKey)
        buildConfigField("String", "PRIVATE_API_KEY", privateKey)


        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Navigation compose navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.icons)

    //Retrofit and OkHttp implementation
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp.client)
    implementation(libs.okhttp.logging.interceptor)

    //Dagger implementation
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    //Coil implementation
    implementation(libs.coil.compose)
}