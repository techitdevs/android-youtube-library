plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    namespace = "com.service.techityoutube"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
        targetSdk = 34

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    

}

dependencies {
    // Core Android dependencies
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
}

afterEvaluate {
    publishing {
        publications {
            register<MavenPublication>("release") {
                groupId = project.findProperty("GROUP_ID") as String? ?: "com.service.techityoutube"
                artifactId = "youtube-player"
                version = project.findProperty("LIBRARY_VERSION") as String? ?: "1.0.0"

                from(components["release"])
                
                pom {
                    name.set("TechIT YouTube Player")
                    description.set("Android YouTube player library with Compose support")
                    url.set("https://github.com/techitdevs/android-youtube-library")
                    
                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }
                    
                    developers {
                        developer {
                            id.set("techit")
                            name.set("TechIT")
                        }
                    }
                }
            }
        }
    }
} 