---
description: Project-specific decisions and ADRs migrated from rules.md
---

# Key Decisions & ADRs

Migrated and initial ADRs for the project. Add further entries with dates and rationale as decisions are made.

| # | Data | Decisao | Status |
|---|------|---------|--------|
| 1 | 2026-02-15 | Adopt Kotlin Multiplatform with targets: JVM, JS(IR) (browser+nodejs), mingwX64 to maximize portability of algorithm implementations. | Aceita |
| 2 | 2026-02-15 | Enforce modular structure: core algorithms in commonMain; platform adapters in target source sets. | Aceita |
