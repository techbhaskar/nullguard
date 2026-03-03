# 🤝 Contributing to NullGuard

Thank you for contributing to NullGuard.

This project follows strict architectural discipline.

------------------------------------------------------------------------

## 1. Architecture Freeze Policy

NullGuard v1.0 architecture is frozen.

No structural changes allowed unless:

-   A critical flaw is identified
-   A formal proposal (RFC) is approved

See docs/architecture.md

------------------------------------------------------------------------

## 2. Development Principles

-   Deterministic analysis only
-   No runtime execution
-   Immutable IR models
-   Pure static analysis
-   Thread-safe implementations
-   Clean separation between modules

------------------------------------------------------------------------

## 3. Branching Strategy

-   main → stable
-   develop → active development
-   feature/\* → individual features
-   fix/\* → bug fixes

------------------------------------------------------------------------

## 4. Commit Standards

Use conventional commits:

feat: add null-state propagation engine\
fix: correct merge lattice behavior\
refactor: simplify CFG builder\
test: add risk propagation test cases

------------------------------------------------------------------------

## 5. Code Style

-   Java 17+
-   No Lombok in core engine
-   Avoid reflection
-   Prefer immutability
-   Clear separation of concerns

------------------------------------------------------------------------

## 6. Testing Requirements

-   Unit tests required
-   Minimum 80% coverage
-   Deterministic test results
-   No flaky tests

------------------------------------------------------------------------

## 7. What Not to Contribute (v1.0)

-   Runtime agents
-   IDE plugins
-   AI/ML models
-   Auto-fix rewriting
-   Cloud dashboards
-   Bytecode analysis

These belong to future roadmap versions.

------------------------------------------------------------------------

NullGuard is a stability intelligence platform.

Build it carefully.
