# Add New Data Structure (Scaffold)

Source: docs/DATA_STRUCTURE_GUIDE.md + src/commonMain

Description:
Steps to add a new data structure implementation with tests and documentation. Use when: creating new DS in the library.

What it teaches
- How to add a data structure (API, tests, examples) consistent with project conventions.

Instructions
1. Implementation path:
   - src/commonMain/kotlin/<package>/datastructures/<name>/<Files>.kt
   - Provide public API, constructors, and utility functions.
2. Tests:
   - src/commonTest/kotlin/.../<name>/NameTest.kt (kotlin.test)
   - Cover corner cases, nullability, and performance where relevant.
3. Examples:
   - Add a usage snippet to docs/USAGE_EXAMPLES.md.
4. Docs:
   - Update docs/DATA_STRUCTURE_GUIDE.md and ALGORITHM_CATALOG.md accordingly.
5. Run:
   - ./gradlew check

References
- docs/DATA_STRUCTURE_GUIDE.md
- docs/USAGE_EXAMPLES.md
