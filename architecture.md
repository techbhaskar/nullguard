# NullGuard – Architecture Document

## 1. High-Level Architecture

NullGuard follows a modular static analysis architecture.

Modules:

nullguard-core
nullguard-rules
nullguard-cli
nullguard-maven-plugin
nullguard-report
nullguard-agent (future)

---

## 2. Core Components

### 2.1 File Scanner

- Recursively scans Java source directory
- Filters .java files
- Parallel processing support

---

### 2.2 Parser Layer

Uses JavaParser to convert source files into AST (CompilationUnit).

Flow:

File → AST → Rule Engine → Issues

---

### 2.3 Rule Engine

Interface:

public interface AnalysisRule {
    List<Issue> analyze(CompilationUnit cu, File file);
    String getRuleId();
}

Responsibilities:
- Execute rules
- Aggregate issues
- Track metrics

---

### 2.4 Issue Model

Fields:

- ruleId
- severity
- packageName
- className
- methodName
- lineNumber
- message

---

### 2.5 Reporting Engine

Generates:

1. JSON structured output
2. HTML dashboard
3. CLI summary

Hierarchy:

Project
  ├── Package
  │    ├── Class
  │    │    ├── Method
  │    │         ├── Issues

---

## 3. Concurrency Model

- ForkJoinPool for file parsing
- Thread-safe issue aggregation
- Immutable Issue objects

---

## 4. Performance Strategy

- Avoid full-project in-memory storage
- Stream-based file analysis
- Cache AST only when needed
- Configurable rule execution

---

## 5. Extensibility Design

To add new rule:

1. Implement AnalysisRule
2. Register in RuleRegistry
3. Add rule metadata

Future:
ServiceLoader for auto-discovery.

---

## 6. Failure Handling

- File parse failures logged but do not stop analysis
- Invalid Java files skipped
- Rule execution isolation (one rule failure does not stop others)

---

## 7. Security

- No runtime execution of project code
- Static analysis only
- No external network calls

---

## 8. Future Architecture

- Add Control Flow Graph builder
- Add Null State Engine
- Add Bytecode analyzer module
- Add Java Agent instrumentation layer