plugins {
    kotlin("multiplatform")
    id("maven-publish")
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

publishing {
    publications.withType<MavenPublication> {
        pom {
            name.set("algoritmos-otimizacao-sample")
            description.set("Exemplos e demonstrações de uso da biblioteca algoritmos-otimizacao.")
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
