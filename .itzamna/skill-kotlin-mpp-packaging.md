# Kotlin MPP Packaging

Source: build.gradle.kts + gradle.properties

Description:
How to produce artifacts for JVM, JS, and Native targets and prepare for publishing. Use when: preparing releases or publishing snapshots.

What it teaches
- Producing compiled artifacts for configured targets (jvm, js(IR), mingwX64) and publishing with maven-publish.

Instructions
1. Build artifacts for all targets:
   - ./gradlew build
2. Inspect specific target outputs:
   - JVM artifacts in build/libs
   - JS browser/node artifacts under build/distributions or build/js
   - Native (mingwX64) in build/bin/native
3. Publish to configured repository:
   - ./gradlew publish
   - Ensure publishing credentials/config are configured in Gradle properties or CI secrets (do NOT commit secrets).
4. When updating Kotlin version, change kotlin.version in gradle.properties and run:
   - ./gradlew wrapper --gradle-version <if gradle upgrade needed> && ./gradlew build

Examples
- Local build + inspect:
  ./gradlew clean build && ls build/libs

References
- build.gradle.kts
- gradle.properties
- Kotlin Multiplatform docs: https://kotlinlang.org/docs/multiplatform.html
