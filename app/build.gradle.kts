plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-android")
    id("kotlin-kapt") // Adicione esta linha para habilitar o kapt
    id("com.google.dagger.hilt.android") version "2.44" apply true // Defina explicitamente a versão
}

android {
    namespace = "com.leofranc.violao_suite"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.leofranc.violao_suite"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "0.1.1"

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
    // Dependências do Compose UI e Material Design 3
    implementation("androidx.compose.material3:material3:1.1.0")
    implementation("androidx.compose.ui:ui:1.4.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.4.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.0")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("androidx.compose.material:material:1.4.0")

    // Dependência para navegação com Compose
    implementation("androidx.navigation:navigation-compose:2.7.1")

    // Para o JSON dos acordes
    implementation("com.google.code.gson:gson:2.8.8")

    // Dependências do Room Database
    implementation("androidx.room:room-runtime:2.5.0")
    implementation("androidx.room:room-ktx:2.5.0")
    kapt("androidx.room:room-compiler:2.5.0")
    implementation ("com.google.dagger:hilt-android:2.44") // Substitua pela versão mais recente
    kapt ("com.google.dagger:hilt-compiler:2.44")

    // Dependências adicionais
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Testes e debug
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

kapt {
    arguments {
        arg("room.schemaLocation", "$projectDir/schemas")
    }
}