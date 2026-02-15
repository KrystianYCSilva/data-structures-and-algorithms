---
description: Architectural rules for the project and module boundaries.
---

# Architectural Rules

Project architectural rules tailored for a Kotlin Multiplatform library focused on algorithms and data structures.

- Rule 1: Prefer modular KMP modules — keep core algorithm implementations in src/commonMain and platform adapters in jvmMain/jsMain/nativeMain to maximize reuse and reduce platform-specific drift.
- Rule 2: Prefer Composition over Inheritance; use interface delegation (by) for cross-cutting behaviours and avoid deep inheritance hierarchies.
- Rule 3: Use explicit visibility (internal for module-internal APIs) to minimize the public surface area.
- Rule 4: Keep pure algorithm logic free of platform-side effects; side-effecting code belongs to adapters or integration layers.
