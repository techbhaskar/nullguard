# Contributing to NullGuard

Thank you for your interest in contributing to NullGuard!

## How to add a new rule

1. Create a new class in the `nullguard-rules` module under `io.nullguard.rules`.
2. Implement the `io.nullguard.core.AnalysisRule` interface.
3. Your rule should implement the `analyze()` method using JavaParser to traverse the AST.
4. Add the rule to `io.nullguard.rules.RuleRegistry`.
5. Add unit tests in `nullguard-rules/src/test/java/io/nullguard/rules`.

## Code Style

- We enforce checkstyle rules (see `checkstyle.xml`).
- Run `mvn checkstyle:check` locally to verify before pushing code.
- Ensure that imports are optimized and there are no unused imports.

## Pull Request Guidelines

- Ensure your branch builds cleanly using `mvn clean install`.
- All new features and rules must have accompanying unit tests.
- Reference any open issue related to your PR, if applicable.
