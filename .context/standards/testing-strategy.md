---
description: Testing strategy and test levels for the project (unit, integration, property-based, CI gating).
---

# Testing Strategy

Testing strategy for the Kotlin Multiplatform algorithms library.

- Unit tests: Place platform-agnostic tests in commonTest using kotlin.test; keep tests fast and deterministic.
- JVM tests: Use jvmTest for JVM-specific behaviors and integration with JVM libraries.
- JS tests: Use jsTest with Karma + ChromeHeadless for browser-related logic when required.
- Property-based tests: Use kotest property checks (checkAll / forAll) for invariants in algorithm implementations where applicable.
- Integration tests: CI-gated, run in a separate job to keep pull request feedback fast.
- Coverage: Aim for high coverage on core algorithm modules; prefer targeted tests over broad, flaky suites.
