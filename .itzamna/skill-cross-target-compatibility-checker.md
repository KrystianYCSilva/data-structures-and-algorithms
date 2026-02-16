# Cross-Target Compatibility Checker

Source: src/commonMain/* + build.gradle.kts

Description:
Checklist to verify API in commonMain remains platform-agnostic and compile across JVM/JS/Native. Use when: merging changes touching commonMain.

What it teaches
- A step-by-step checklist to ensure changes compile and behave across all configured targets.

Instructions
1. Compile all targets:
   - ./gradlew build
2. Run common and target tests:
   - ./gradlew jvmTest jsTest
3. Check for expect/actual usages and platform-specific APIs in commonMain.
   - Grep for platform APIs (e.g., java.* or platform-specific libs).
4. Verify native target artifacts compile:
   - ./gradlew assembleNative (if provided) or inspect native build outputs.
5. Run static checks:
   - Use IDE inspections for expect/actual mismatches.
6. If adding platform-specific code, add proper sourceSet (jvmMain/jsMain/nativeMain) and keep API stable.

Examples
- Before merge: ./gradlew clean build && ./gradlew jvmTest jsTest

References
- build.gradle.kts
- Kotlin Multiplatform docs: https://kotlinlang.org/docs/multiplatform.html
