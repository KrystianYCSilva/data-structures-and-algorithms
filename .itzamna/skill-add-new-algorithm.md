# Add New Algorithm (Scaffold)

Source: docs/IMPLEMENTATION_PLAN.md + src/commonMain

Description:
Scaffold pattern for adding a new algorithm implementation, tests, and docs. Use when: introducing a new algorithm in src/commonMain/kotlin.

What it teaches
- Standard steps and file locations for adding algorithms so they are multiplatform and documented.

Instructions
1. Create implementation under:
   - src/commonMain/kotlin/<package>/algorithms/<category>/NewAlgorithm.kt
   Use idiomatic Kotlin and avoid platform APIs.
2. Add unit tests:
   - src/commonTest/kotlin/<package>/algorithms/<category>/NewAlgorithmTest.kt
   Use kotlin.test for assertions.
3. Add usage example (docs/USAGE_EXAMPLES.md) snippet showing API usage.
4. Update docs:
   - docs/ALGORITHM_CATALOG.md (add entry & status)
   - docs/PROJECT_ROADMAP.md if applicable
5. Run tests:
   - ./gradlew check
6. Follow code-style: kotlin.code.style=official and add documentation KDoc for public API.

Examples
- Minimal algorithm skeleton (description only; do NOT paste code): file NewAlgorithm.kt with a public function `fun newAlgorithm(input: List<Int>): Result`

References
- docs/IMPLEMENTATION_PLAN.md
- docs/ALGORITHM_CATALOG.md
