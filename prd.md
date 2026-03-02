# NullGuard – Product Requirements Document (PRD)

## 1. Overview

NullGuard is an open-source static analysis tool designed to detect potential runtime failures in Java applications before production deployment.

Initial focus:
- NullPointerException detection
- Optional misuse
- Autounboxing null risks
- Map.get() without null validation
- Unsafe field injection patterns

NullGuard aims to be:
- Lightweight
- Developer-first
- CI/CD friendly
- Rule-extensible
- Open-source community driven

---

## 2. Problem Statement

In large Java systems, runtime failures such as NullPointerException are often discovered only in staging or production environments.

Existing tools:
- SonarQube (heavy setup)
- SpotBugs (generic)
- Error Prone (compiler plugin)

There is no lightweight, crash-focused analyzer designed specifically for production-readiness validation.

---

## 3. Goals

### Primary Goals
- Detect possible NullPointerException paths
- Generate structured report (HTML + JSON)
- Integrate with Maven
- Provide CLI execution
- Allow rule extensibility

### Secondary Goals
- Risk severity scoring
- CI/CD integration
- Rule suppression mechanism
- Dashboard report visualization

---

## 4. Non-Goals (MVP)

- 100% accurate null analysis (undecidable problem)
- Deep inter-procedural analysis (future version)
- IDE plugin (future)
- Runtime Java Agent (future phase)

---

## 5. Functional Requirements

### FR1 – Project Scanning
- Scan src/main/java directory
- Support Java 8–21
- Parse all .java files

### FR2 – Rule Engine
- Apply multiple analysis rules
- Each rule must implement a common interface
- Aggregate issues across project

### FR3 – Reporting
- Generate:
  - HTML report
  - JSON report
  - CLI summary

### FR4 – Maven Plugin
- Execute via:
  mvn nullguard:analyze

### FR5 – Suppression
- Allow:
  @SuppressNullGuard("RULE_ID")

---

## 6. Non-Functional Requirements

- Must analyze 1000+ files under 30 seconds
- Parallel file processing
- Low memory footprint
- Thread-safe engine
- Apache 2.0 License

---

## 7. Success Metrics

- <5% false positive rate (target)
- CI adoption capability
- Community rule contributions
- GitHub stars > 500 within 6 months

---

## 8. Roadmap

Phase 1:
- AST-based analysis
- NPE detection
- HTML report

Phase 2:
- Control flow graph
- Data flow null tracking
- Severity scoring engine

Phase 3:
- Bytecode analysis
- Java Agent
- IntelliJ plugin