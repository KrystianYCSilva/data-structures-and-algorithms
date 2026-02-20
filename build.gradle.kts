import org.gradle.api.GradleException
import org.gradle.api.publish.PublishingExtension
import org.gradle.plugins.signing.SigningExtension

plugins {
    kotlin("multiplatform") apply false
}

group = "br.uem.din"
version = "0.1.0"

allprojects {
    repositories {
        mavenCentral()
        google()
    }
}

subprojects {
    group = rootProject.group
    version = rootProject.version

    plugins.withId("maven-publish") {
        extensions.configure<PublishingExtension> {
            repositories {
                maven {
                    name = "sonatype"
                    val releasesRepoUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                    val snapshotsRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
                    url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl

                    credentials {
                        username = (findProperty("ossrhUsername") as String?) ?: System.getenv("OSSRH_USERNAME")
                        password = (findProperty("ossrhPassword") as String?) ?: System.getenv("OSSRH_PASSWORD")
                    }
                }
            }
        }

        pluginManager.apply("signing")
        extensions.configure<SigningExtension> {
            val signingKey = (findProperty("signingKey") as String?) ?: System.getenv("SIGNING_KEY")
            val signingPassword = (findProperty("signingPassword") as String?) ?: System.getenv("SIGNING_PASSWORD")

            if (!signingKey.isNullOrBlank() && !signingPassword.isNullOrBlank()) {
                useInMemoryPgpKeys(signingKey, signingPassword)
                sign(extensions.getByType<PublishingExtension>().publications)
            }
        }
    }
}

tasks.register("releasePreflight") {
    group = "publishing"
    description = "Validates credentials and signing setup for release publication"

    doLast {
        val required = mapOf(
            "ossrhUsername/OSSRH_USERNAME" to ((findProperty("ossrhUsername") as String?) ?: System.getenv("OSSRH_USERNAME")),
            "ossrhPassword/OSSRH_PASSWORD" to ((findProperty("ossrhPassword") as String?) ?: System.getenv("OSSRH_PASSWORD")),
            "signingKey/SIGNING_KEY" to ((findProperty("signingKey") as String?) ?: System.getenv("SIGNING_KEY")),
            "signingPassword/SIGNING_PASSWORD" to ((findProperty("signingPassword") as String?) ?: System.getenv("SIGNING_PASSWORD"))
        )

        val missing = required.filterValues { it.isNullOrBlank() }.keys
        if (missing.isNotEmpty()) {
            throw GradleException(
                "Release publishing is not configured. Missing: ${missing.joinToString(", ")}. " +
                    "Configure via gradle.properties or environment variables."
            )
        }
    }
}
