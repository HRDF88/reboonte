import com.android.build.gradle.BaseExtension

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.dagger.hilt.android")
    id("jacoco")
    id("org.sonarqube") version "4.4.1.3373"
    kotlin("kapt")
    id("com.google.gms.google-services")
    id("org.jetbrains.kotlin.plugin.compose") version "2.1.0"
}

sonarqube {
    properties {
        property("sonar.organization", "Julien_Seguin") // organisation SonarCloud
        property("sonar.projectKey", "HRDF88_reboonte")    // identifiant de projet SonarCloud
        property("sonar.projectName", "reboonte") // Nom de  projet SonarCloud
        property("sonar.host.url", "https://sonarcloud.io") // URL de SonarCloud
        property("sonar.login", project.findProperty("sonar.token") ?: "") // Token généré dans SonarCloud
        property("sonar.coverage.jacoco.xmlReportPaths", "${layout.buildDirectory.get().asFile}/reports/jacoco/testDebugUnitTest/jacocoTestReport.html")
    }
}
android {
    namespace = "com.openclassrooms.rebonnte"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.openclassrooms.rebonnte"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE-notice.md"
            excludes += "META-INF/NOTICE"
            excludes += "META-INF/NOTICE.md"
            excludes += "META-INF/LICENSE"
            excludes += "META-INF/*.kotlin_module"
        }
    }
}
val androidExtension = extensions.getByType<BaseExtension>()


tasks.register<JacocoReport>("jacocoTestReport") {
    dependsOn("testDebugUnitTest")

    reports {
        xml.required.set(true)
        html.required.set(true)
        html.outputLocation.set(layout.buildDirectory.dir("reports/jacoco/testDebugUnitTest/html"))

    }

    val fileFilter = listOf(
        "**/R.class",
        "**/R$*.class",
        "**/BuildConfig.*",
        "**/Manifest*.*",
        "**/*Test*.*",
        "android/**/*.*"
    )

    val debugTree = fileTree("${buildDir}/intermediates/javac/debug") {
        exclude(fileFilter)
    }

    val kotlinDebugTree = fileTree("${buildDir}/tmp/kotlin-classes/debug") {
        exclude(fileFilter)
    }

    classDirectories.setFrom(files(debugTree, kotlinDebugTree))
    sourceDirectories.setFrom(files("src/main/java", "src/main/kotlin"))
    executionData.setFrom(fileTree(buildDir).include(
        "jacoco/testDebugUnitTest.exec",
        "outputs/unit_test_code_coverage/debugUnitTest/testDebugUnitTest.exec"
    ))
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")
    implementation(libs.androidx.activity.compose)

    // Compose BOM - gère les versions Compose automatiquement
    implementation(platform(libs.androidx.compose.bom))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material:material")
    implementation("androidx.compose.ui:ui-tooling-preview")

    implementation(libs.androidx.material3)
    implementation("androidx.navigation:navigation-compose:2.9.0")
    implementation(libs.androidx.room.runtime.android)
    implementation(libs.androidx.navigation.testing.android)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Hilt
    implementation("com.google.dagger:hilt-android:2.51.1")
    implementation("androidx.hilt:hilt-navigation-fragment:1.2.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.51.1")
    androidTestImplementation ("androidx.hilt:hilt-navigation-compose:1.2.0")
    kapt("com.google.dagger:hilt-compiler:2.51.1")

    // Firebase (BoM centralise les versions)
    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")
    implementation("androidx.browser:browser:1.6.0")
    implementation("com.google.android.gms:play-services-auth:20.7.0")

    // Firebase UI Auth
    implementation("com.firebaseui:firebase-ui-auth:9.0.0")

    // Tests
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.0.0")
    testImplementation("org.mockito:mockito-inline:5.2.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    testImplementation(kotlin("test"))
    androidTestImplementation("org.mockito:mockito-android:5.2.0")
    testImplementation("io.mockk:mockk-android:1.13.7")
    androidTestImplementation("io.mockk:mockk-android:1.13.7")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0")
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    testImplementation("com.google.truth:truth:1.4.0")
    testImplementation("app.cash.turbine:turbine:1.1.0")
    androidTestImplementation("androidx.navigation:navigation-testing-android:2.9.0")




}
kapt {
    correctErrorTypes = true
}