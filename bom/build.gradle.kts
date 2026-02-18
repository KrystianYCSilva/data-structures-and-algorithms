plugins {
    `java-platform`
    id("maven-publish")
}

javaPlatform {
    allowDependencies()
}

dependencies {
    constraints {
        api(project(":datastructures"))
        api(project(":algorithms"))
        api(project(":extensions"))
        api(project(":optimization"))
    }
}

publishing {
    publications {
        create<MavenPublication>("bom") {
            from(components["javaPlatform"])

            pom {
                name.set("algoritmos-otimizacao-bom")
                description.set("Bill of Materials (BOM) para a biblioteca acadêmica Kotlin Multiplatform de estruturas de dados, algoritmos e otimização.")
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
}
