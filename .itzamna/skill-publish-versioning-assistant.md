# Publish & Versioning Assistant

Source: gradle.properties + build.gradle.kts + docs/PROJECT_ROADMAP.md

Description:
Steps to bump versions, prepare changelogs, and publish via Gradle. Use when: preparing a release.

What it teaches
- How to prepare a release: bump version, update docs, run tests, publish artifact.

Instructions
1. Update project version in build.gradle.kts or central location (here version is set in build.gradle.kts).
2. Update CHANGELOG or docs/PROJECT_ROADMAP.md with release notes.
3. Run full verification:
   - ./gradlew clean build check
4. Publish:
   - ./gradlew publish (ensure repository credentials are available in CI or local environment variables)
5. Tag the release in Git and push tags:
   - git tag -a vX.Y.Z -m "Release X.Y.Z"
   - git push origin vX.Y.Z

Examples
- Prepare release 1.1.0:
  - edit version -> run tests -> ./gradlew publish -> tag and push

References
- build.gradle.kts
- docs/PROJECT_ROADMAP.md
- Gradle docs: https://docs.gradle.org/current/userguide/publishing_maven.html
