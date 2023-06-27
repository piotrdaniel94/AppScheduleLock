/*
 * Copyright 2023 Stanislav Aleshin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * imitations under the License.
 */
plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    mavenLocal()
    gradlePluginPortal()
    google()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.20")
    implementation("com.android.tools.build:gradle:7.4.2")
    implementation("com.google.gms:google-services:4.3.15")
    implementation("com.google.firebase:firebase-crashlytics-gradle:2.9.5")
}
