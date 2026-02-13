plugins {
    kotlin("multiplatform")
    id("maven-publish")
}

group = "br.uem.din"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    google()
}

kotlin {
    jvm {
        withJava()
    }
    mingwX64("native") {
        // Default binaries for KMP library usage are sufficient.
        // If C-interop is needed, sharedLib() can be re-added.
    }

    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting
        val nativeMain by getting
    }
}

