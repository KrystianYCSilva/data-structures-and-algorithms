pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }

    plugins {
        kotlin("multiplatform").version(extra["kotlin.version"] as String)
    }
}

rootProject.name = "algoritmos-otimizacao"

include(":datastructures")
include(":algorithms")
include(":extensions")
include(":optimization")
include(":bom")
include(":sample")
