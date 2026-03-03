# 🚀 NullGuard v1.0

### Stability Intelligence Engine for Java

> Static nullability analysis + systemic risk propagation + stability
> scoring.

NullGuard is an enterprise-grade static analysis engine that transforms
null pointer detection into full-system stability intelligence.

------------------------------------------------------------------------

## 🎯 What NullGuard Does

NullGuard analyzes Java codebases and computes:

-   🔍 Null dereference risks
-   📊 Stability Index (0--100)
-   🌐 Risk propagation across call graph
-   📜 API contract violations
-   💥 Blast radius modeling
-   💡 Context-aware fix suggestions

All performed **statically** using AST analysis.

No runtime instrumentation.\
No bytecode manipulation.\
No execution required.

------------------------------------------------------------------------

## 🧠 Core Capabilities (v1.0)

-   Control Flow Graph (CFG) per method
-   Null-state propagation engine
-   Inter-procedural Method Summaries
-   Global Call Graph construction
-   Risk propagation engine
-   Stability scoring engine
-   API Contract Stability Analyzer
-   Suggestion ranking engine
-   JSON + DOT visualization export
-   CLI interface
-   Maven plugin integration

------------------------------------------------------------------------

## 🏗 Architecture Overview

Execution pipeline:

AST → CFG → Null Analysis → Method Summary\
→ Call Graph → Risk Propagation\
→ Stability Scoring → Contracts\
→ Suggestions → Visualization

Full architecture spec available in:

docs/architecture.md

------------------------------------------------------------------------

## 📊 Stability Index

NullGuard introduces a **Stability Index (0--100)**:

  Score    Grade
  -------- -------
  90+      A
  80--89   B
  70--79   C
  60--69   D
  \<60     F

This score reflects:

-   Intrinsic method risk
-   Propagated instability
-   Contract violations
-   Blast radius impact

------------------------------------------------------------------------

## ⚙ Configuration

``` yaml
nullguard:
  decayFactor: 0.6
  failBuildIfStabilityBelow: 75
  highRiskThreshold: 70
  propagationDepthLimit: 10
```

------------------------------------------------------------------------

## 📦 Module Structure

nullguard-core\
nullguard-analysis\
nullguard-callgraph\
nullguard-scoring\
nullguard-suggestions\
nullguard-visualization\
nullguard-cli\
nullguard-maven-plugin

------------------------------------------------------------------------

## 🛠 Installation (Planned)

``` xml
<plugin>
  <groupId>io.nullguard</groupId>
  <artifactId>nullguard-maven-plugin</artifactId>
  <version>1.0.0</version>
</plugin>
```

------------------------------------------------------------------------

## 🚫 Out of Scope (v1.0)

-   Runtime agents
-   Bytecode analysis
-   CVE scanning
-   IDE plugins
-   Auto-fix rewriting
-   SaaS dashboard

------------------------------------------------------------------------

## 🏁 Status

**Version:** 1.0.0\
**Architecture:** Frozen\
**Implementation:** In Progress

------------------------------------------------------------------------
