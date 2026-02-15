---
description: Deployment and release workflow for artifacts and documentation.
---

# Deployment Workflow

Deployment workflow notes for library artifacts and documentation.

- Build: Use Gradle wrapper (gradlew.bat build) to produce artifacts for all targets.
- Publication: Publish JVM/JS artifacts to Maven repositories via Gradle publishing; include Dokka-generated docs as part of the publication.
- CI: Separate jobs for build, tests (jvmTest/jsTest), and documentation; only publish on successful release-tagged builds.
- Rollback: Use artifact versioning and do not overwrite published artifacts; create a follow-up patch/release to correct issues.
