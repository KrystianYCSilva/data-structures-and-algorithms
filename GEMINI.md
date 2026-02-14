# Gemini Project Context: algoritmos_otimizacao

## Project Overview

This project, `algoritmos_otimizacao`, is a comprehensive library of data structures, fundamental algorithms, and optimization heuristics. It is built as a Kotlin Multiplatform project, designed to be a robust, academic-focused resource. The codebase is structured to be shared across platforms, currently targeting the JVM and native environments (MinGW x64).

The library is extensive, containing implementations for dozens of algorithms across various categories as detailed in the project's documentation.

**Core Technologies:**
*   **Language:** Kotlin
*   **Framework:** Kotlin Multiplatform
*   **Build Tool:** Gradle

## Project Structure

The project follows the standard Kotlin Multiplatform directory structure:

-   `src/commonMain/kotlin/`: Contains the platform-independent Kotlin code, which is the vast majority of the project. The core logic for all data structures and algorithms resides here.
-   `src/jvmMain/kotlin/`: Contains platform-specific implementations for the JVM target.
-   `src/nativeMain/kotlin/`: Contains platform-specific implementations for the Native (MinGW x64) target.
-   `src/test/kotlin/`: Contains unit tests for the common code, written using the `kotlin.test` library.
-   `docs/`: This directory contains extensive project documentation, including the project roadmap, a catalog of all implemented algorithms, and usage examples.

## Building and Running

The project is managed using a Gradle wrapper.

### Build the Project

To compile the code for all targets and run build tasks, use the following command:

```bash
./gradlew build
```

### Run Tests

To execute the unit tests and other verification tasks, use the `check` command. This ensures that the code is correct and adheres to quality standards.

```bash
./gradlew check
```

## Development Conventions

-   **Code Implementation:** New algorithms and data structures should be added to the `src/commonMain/kotlin` directory to ensure they are available for all platforms.
-   **Testing:** All new features, algorithms, or bug fixes must be accompanied by corresponding unit tests in the `src/test/kotlin` directory. The project uses the `kotlin.test` framework for assertions and test structure.
-   **Documentation:** The project prides itself on its detailed documentation. When adding new components or making significant changes, it is crucial to update the following files in the `docs/` directory:
    -   `PROJECT_ROADMAP.md`: To reflect progress on the project's long-term goals.
    -   `ALGORITHM_CATALOG.md`: To add the new algorithm and its implementation status.
    -   `USAGE_EXAMPLES.md`: To provide clear examples of how to use the new feature.
