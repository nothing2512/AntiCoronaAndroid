import org.gradle.api.artifacts.dsl.RepositoryHandler

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

object Deps {

    const val pluginId = "com.android.application"
    const val pluginAndroid = "android"
    const val pluginExtensions = "android.extensions"
    const val pluginKapt = "kapt"

    const val instrumentRunner = "androidx.test.runner.AndroidJUnitRunner"

    const val shimmer = "com.facebook.shimmer:shimmer:${Versions.shimmers}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timbers}"
    const val junit = "junit:junit:${Versions.junits}"
    const val mockWebServer = "com.squareup.okhttp3:mockwebserver:${Versions.mockwebserver}"
    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.androidGradlePlugins}"
    const val coil = "io.coil-kt:coil:${Versions.coils}"
    const val font = "uk.co.chrisjenx:calligraphy:${Versions.fonts}"
    const val archCoreTesting = "androidx.arch.core:core-testing:${Versions.archCore}"

    val repositories: RepositoryHandler.() -> Unit = {
        google()
        jcenter()
        mavenCentral()
    }

    object Coroutine {

        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutine}"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutine}"
        const val androidCoroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutine}"
    }

    object Test {
        const val hamcrest = "org.hamcrest:hamcrest-all:${Versions.hamcrests}"
        const val junits = "androidx.test.ext:junit:${Versions.test}"
        const val core = "androidx.test:core:${Versions.test}"
        const val rules = "androidx.test:rules:${Versions.test}"
    }

    object Room {
        const val runtime = "androidx.room:room-runtime:${Versions.room}"
        const val compiler = "androidx.room:room-compiler:${Versions.room}"
    }

    object Support {
        const val annotations = "androidx.annotation:annotation:${Versions.support}"
        const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompats}"
        const val recyclerview = "androidx.recyclerview:recyclerview:${Versions.support}"
        const val cardview = "androidx.cardview:cardview:${Versions.support}"
        const val design = "com.google.android.material:material:${Versions.support}"
        const val legacy = "androidx.legacy:legacy-support-v4:${Versions.support}"
        const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtxs}"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayouts}"
    }

    object Lifecycle {
        const val runtime = "androidx.lifecycle:lifecycle-runtime:${Versions.lifecycle}"
        const val java8 = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}"
        const val compiler = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycle}"
        const val viewmodelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
        const val livedataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    }

    object Mockk {
        const val test = "io.mockk:mockk:${Versions.mockk}"
        const val instrumented = "io.mockk:mockk-android:${Versions.mockk}"
    }

    object Retrofit {
        const val gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
        const val runtime = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    }

    object Espresso {
        const val core = "androidx.test.espresso:espresso-core:${Versions.espresso}"
        const val contrib = "androidx.test.espresso:espresso-contrib:${Versions.espresso}"
        const val intents = "androidx.test.espresso:espresso-intents:${Versions.espresso}"
        const val idling = "androidx.test.espresso:espresso-idling-resource:${Versions.espresso}"
    }

    object Kotlin {
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
        const val plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    }

    object Koin {
        const val core = "org.koin:koin-core:${Versions.koin}"
        const val ext = "org.koin:koin-core-ext:${Versions.koin}"
        const val test = "org.koin:koin-test:${Versions.koin}"
        const val scope = "org.koin:koin-androidx-scope:${Versions.koin}"
        const val viewmodel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"
        const val androidx = "org.koin:koin-androidx-ext:${Versions.koin}"
    }
}