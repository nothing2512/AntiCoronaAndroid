/*
 * Copyright 2020 Nothing
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

plugins {
    id(Deps.pluginId)
    kotlin(Deps.pluginAndroid)
    kotlin(Deps.pluginExtensions)
    kotlin(Deps.pluginKapt)
}

android {
    compileSdkVersion(Versions.targetSdk)
    buildToolsVersion(Versions.buildTools)
    defaultConfig {
        applicationId = Versions.appId
        minSdkVersion(Versions.minSdk)
        targetSdkVersion(Versions.targetSdk)
        versionCode = Versions.code
        versionName = Versions.name
        testInstrumentationRunner  = Deps.instrumentRunner
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            buildConfigField("String", "BASE_URL", "\"https://nothing.mwebs.id/v1/\"")
        }
        getByName("debug") {
            isDebuggable = true
            buildConfigField("String", "BASE_URL", "\"https://nothing.mwebs.id/v1/\"")

        }
    }
    dataBinding {
        isEnabled = true
    }
    androidExtensions {
        isExperimental = true
    }
    kotlinOptions {
        jvmTarget = Versions.jvmTarget
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    configurations {
        implementation {
            resolutionStrategy.failOnVersionConflict()
        }
    }
}


dependencies  {
    implementation(Deps.Support.appCompat)
    implementation(Deps.Support.recyclerview)
    implementation(Deps.Support.cardview)
    implementation(Deps.Support.design)
    implementation(Deps.Support.coreKtx)
    implementation(Deps.Support.annotations)
    implementation(Deps.Support.legacy)
    implementation(Deps.Support.design)
    implementation(Deps.Support.constraintLayout)

    implementation(Deps.Coroutine.core)
    implementation(Deps.Coroutine.androidCoroutine)

    implementation(Deps.Lifecycle.viewmodelKtx)
    implementation(Deps.Lifecycle.livedataKtx)
    implementation(Deps.Lifecycle.runtime)
    implementation(Deps.Lifecycle.java8)

    implementation(Deps.Retrofit.runtime)
    implementation(Deps.Retrofit.gson)

    implementation(Deps.Kotlin.stdlib)
    implementation(Deps.Espresso.idling)

    implementation(Deps.timber)
    implementation(Deps.shimmer)
    implementation(Deps.coil)
    implementation(Deps.font)
    implementation(Deps.chart)

    implementation(Deps.Room.runtime)

    implementation(Deps.Koin.core)
    implementation(Deps.Koin.ext)
    implementation(Deps.Koin.viewmodel)
    implementation(Deps.Koin.scope)
    implementation(Deps.Koin.androidx)

     add ("kapt", Deps.Room.compiler)
     add ("kapt", Deps.Lifecycle.compiler)

    testImplementation(Deps.Coroutine.test)
    testImplementation(Deps.junit)
    testImplementation(Deps.mockWebServer)
    testImplementation(Deps.archCoreTesting)
    testImplementation(Deps.Mockk.test)
    testImplementation(Deps.Test.hamcrest)
    testImplementation(Deps.Koin.test)

    add("androidTestImplementation", Deps.junit)

    add("androidTestImplementation", Deps.Support.appCompat)
    add("androidTestImplementation", Deps.Support.recyclerview)
    add("androidTestImplementation", Deps.Support.cardview)
    add("androidTestImplementation", Deps.Support.design)

    add("androidTestImplementation", Deps.Espresso.core)
    add("androidTestImplementation", Deps.Espresso.contrib)
    add("androidTestImplementation", Deps.Espresso.intents)

    add("androidTestImplementation", Deps.archCoreTesting)
    add("androidTestImplementation", Deps.Mockk.instrumented)

    add("androidTestImplementation", Deps.Test.junits)
    add("androidTestImplementation", Deps.Test.core)
    add("androidTestImplementation", Deps.Test.rules)
}