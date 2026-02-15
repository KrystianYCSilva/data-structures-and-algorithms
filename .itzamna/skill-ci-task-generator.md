# CI Task Generator

Source: build.gradle.kts + docs/PROJECT_ROADMAP.md

Description:
Create CI job snippets (Gradle commands and environment notes) for jvm/js/native builds and test runs. Use when: adding or updating CI pipelines.

What it teaches
- Minimal CI job steps for each target and test stage and recommendations for CI images.

Instructions
1. Recommended CI steps:
   - Checkout
   - Cache Gradle wrapper and .gradle
   - Run ./gradlew clean build (use gradlew.bat on Windows runners)
   - Run ./gradlew jvmTest jsTest
2. For JS tests ensure ChromeHeadless installed in CI image or use a Docker image that includes it.
3. For publishing jobs:
   - Run on protected branch only; use ./gradlew publish with credentials injected via CI secrets.
4. Provide sample job YAML snippets for GitHub Actions, GitLab CI, or other CI systems.

Examples
- GitHub Actions job (conceptual):
  - uses: ubuntu-latest
  - steps:
    - uses: actions/checkout@v4
    - run: ./gradlew --no-daemon clean build

References
- build.gradle.kts
- Official Gradle wrapper docs: https://docs.gradle.org/current/userguide/gradle_wrapper.html
