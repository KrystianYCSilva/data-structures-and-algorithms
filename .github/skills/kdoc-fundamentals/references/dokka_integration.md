Dokka Integration Notes

Quick configuration tips for Dokka in Kotlin projects.

- Add plugin: id("org.jetbrains.dokka") version "<version>" in build.gradle.kts.
- Configure dokkaHtml or dokkaHtmlMultiModule outputDirectory to a known path (e.g., build/dokka).
- Invoke via `./gradlew dokkaHtml` and publish the resulting site from CI artifacts.

Example snippet:

plugins {
  id("org.jetbrains.dokka") version "1.8.20"
}

tasks.dokkaHtml {
  outputDirectory.set(buildDir.resolve("dokka"))
}