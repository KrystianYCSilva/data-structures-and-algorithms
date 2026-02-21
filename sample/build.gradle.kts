plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm {
        withJava()
        mainRun {
            mainClass.set("br.uem.din.sample.MainKt")
        }
    }

    jvmToolchain(8)

    js(IR) {
        nodejs()
    }

    mingwX64("native")

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":datastructures"))
                implementation(project(":algorithms"))
                implementation(project(":extensions"))
                implementation(project(":optimization"))
            }
        }
        val jvmMain by getting
        val jsMain by getting
        val nativeMain by getting
    }
}
