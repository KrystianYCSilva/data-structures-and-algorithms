# JS Karma Headless Test Runner

Source: build.gradle.kts (js IR block) + docs/KOTLIN_USAGE.md

Description:
Running and debugging JS tests that use Karma + ChromeHeadless. Use when: JS tests fail or need headless execution on CI.

What it teaches
- How to run JS tests configured with Karma/ChromeHeadless and common troubleshooting steps.

Instructions
1. Run JS tests:
   - ./gradlew jsTest
2. If tests fail locally, run Karma in verbose mode (if available in Gradle task) or run Node test runner:
   - ./gradlew jsTest --info
3. Ensure ChromeHeadless is available in CI:
   - Use official CI images with Chrome or install Chrome before jsTest.
4. For flakiness:
   - Re-run tests with --no-parallel or increase timeouts in Karma config (if exposed).
5. For debugging, run Karma with a visible browser configured (modify testTask to use Chrome instead of ChromeHeadless), then run and open the browser.

Examples
- CI command:
  ./gradlew jsTest --stacktrace

References
- build.gradle.kts (js testTask with useKarma)
- docs/KOTLIN_USAGE.md
- Karma / CI integration docs (search as needed)
