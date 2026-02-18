plugins {
    kotlin("multiplatform") apply false
}

group = "br.uem.din"
version = "0.1.0"

subprojects {
    group = rootProject.group
    version = rootProject.version

    repositories {
        mavenCentral()
        google()
    }
}
