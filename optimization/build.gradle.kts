plugins {
    kotlin("multiplatform")
    id("maven-publish")
}

kotlin {
    explicitApi()

    jvm {
        withJava()
    }

    jvmToolchain(8)

    js(IR) {
        browser {
            testTask {
                useKarma {
                    useChromeHeadless()
                }
            }
        }
        nodejs()
    }

    mingwX64("native")

    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jsMain by getting
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val nativeMain by getting
    }
}

publishing {
    publications.withType<MavenPublication> {
        pom {
            name.set("algoritmos-otimizacao-optimization")
            description.set("Heurísticas e metaheurísticas de otimização Kotlin Multiplatform: HC, SA, TS, GA, PSO, ACO, DE, VNS, MA, LNS e mais.")
            url.set("https://gitlab.com/krystianyago/algoritmos_otimizacao")
            licenses {
                license {
                    name.set("MIT License")
                    url.set("https://opensource.org/licenses/MIT")
                }
            }
            developers {
                developer {
                    id.set("krystianyago")
                    name.set("Krystian Yago C. Silva")
                }
            }
            scm {
                url.set("https://gitlab.com/krystianyago/algoritmos_otimizacao")
                connection.set("scm:git:https://gitlab.com/krystianyago/algoritmos_otimizacao.git")
                developerConnection.set("scm:git:git@gitlab.com:krystianyago/algoritmos_otimizacao.git")
            }
        }
    }
}
