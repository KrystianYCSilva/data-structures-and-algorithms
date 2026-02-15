---
description: Code quality, formatting, static analysis and CI gate expectations.
---

# Code Quality

Standards for code quality, linters, and review process tailored to this repository.

- Formatting: kotlin.code.style=official (use the Gradle wrapper and respect project formatting settings).
- Static Analysis: Run detekt and ktlint in CI for Kotlin code; fail build on critical issues.
- Tests: Unit tests required for all core algorithms; aim for deterministic, fast tests in commonTest.
- Reviews: Enforce changelist-level reviews with at least one reviewer and one approval for non-trivial changes.
- CI Gates: Build, jvmTest, jsTest and documentation generation (Dokka) must pass before merge to main.
