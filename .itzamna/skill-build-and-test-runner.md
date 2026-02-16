# Build & Test Runner

Source: docs/USAGE_EXAMPLES.md + build.gradle.kts

Description:
Short, repeatable recipe for building the project and running tests across targets. Use when: running CI checks, reproducing failures locally, or executing a single test.

What it teaches
- How to run project builds and tests for this Kotlin Multiplatform repository on Windows and POSIX shells.
- How to run a single test on JVM and JS targets.

Instructions
1. Use the Gradle wrapper to guarantee reproducible builds.
   - POSIX: ./gradlew build
   - Windows: gradlew.bat build
2. Run full verification:
   - ./gradlew check
3. Run target-specific tests:
   - JVM: ./gradlew jvmTest
   - JS: ./gradlew jsTest
4. Run a single test (JVM):
   - ./gradlew jvmTest --tests "fully.qualified.TestClassName*"
   Use wildcards to match test classes or methods.
5. Run a single test (JS):
   - ./gradlew jsTest --tests "fully.qualified.TestClassName*"
   Note: JS uses Karma + ChromeHeadless; ensure headless Chrome available in CI.
6. If CI requires specific Gradle version, ensure gradle/wrapper files are present and committed.

Examples
- Reproduce CI locally (POSIX):
  ./gradlew clean build check
- Run only the AStar tests on JVM:
  ./gradlew jvmTest --tests "br.uem.din.algorithms.graph.AStarTest*"

References
- docs/USAGE_EXAMPLES.md (project examples)
- build.gradle.kts (project configuration)
- Official Gradle wrapper docs: https://docs.gradle.org/current/userguide/gradle_wrapper.html
