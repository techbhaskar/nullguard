# NullGuard – Implementation Plan

## Phase 1 – MVP (4 Weeks)

### Week 1 – Core Engine
- Setup multi-module Maven project
- Integrate JavaParser
- Build Rule interface
- Build Issue model
- File scanner service

Deliverable:
Basic CLI that prints detected issues.

---

### Week 2 – Rule Development
Implement rules:

- NG001 – Parameter dereferenced without null check
- NG002 – Optional.get() without isPresent()
- NG003 – Map.get() without null validation
- NG004 – Autounboxing risk
- NG005 – Field injection risk

Deliverable:
Working rule execution pipeline.

---

### Week 3 – Reporting Layer
- JSON report generator
- HTML static report template
- Project summary stats
- Package → Class → Method hierarchy

Deliverable:
target/nullguard-report/index.html

---

### Week 4 – CLI + Maven Plugin
- CLI argument parsing
- Maven plugin integration
- Fail build on CRITICAL severity (optional config)
- GitHub Actions workflow

Deliverable:
Production-ready v1.0.0 release

---

## Phase 2 – Advanced Analysis

- Control Flow Graph builder
- Null state propagation engine
- Inter-procedural call tracing
- Severity scoring engine
- Rule suppression support

---

## Phase 3 – Enterprise Expansion

- Bytecode analysis using ASM
- Java Agent mode
- REST dashboard
- Risk heatmap visualization
- CI annotations

---

## Open Source Governance

- Apache 2.0 License
- CONTRIBUTING.md
- Issue template
- Pull request template
- Code of Conduct