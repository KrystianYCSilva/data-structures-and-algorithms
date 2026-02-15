---
description: Recommended architecture patterns and modularization guidance.
---

# Architecture Patterns

Recommended architecture patterns for this library:

- Modular Library Pattern: core algorithms in commonMain, platform-specific adapters in target source sets (jvmMain, jsMain, nativeMain).
- Hexagonal Adapters: isolate side-effects and I/O in adapter modules to keep core logic pure and testable.
- Explicit Module Boundaries: define clear APIs for each module and use 'internal' visibility to prevent leakage of implementation details.
