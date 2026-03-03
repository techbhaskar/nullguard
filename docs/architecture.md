# 🧠 NullGuard v1.0 Architecture Specification

**Version:** 1.0.0\
**Status:** Frozen (Pre-Implementation Baseline)

------------------------------------------------------------------------

## 1. Architectural Vision

NullGuard is a static inter-procedural stability analysis engine for
Java.

It performs:

-   Null-state tracking
-   Method summary extraction
-   Global call graph modeling
-   Risk propagation
-   Stability scoring
-   API contract validation
-   Suggestion ranking
-   Graph export

No runtime instrumentation. AST-only analysis in v1.0.

------------------------------------------------------------------------

## 2. Layered Execution Model

Layer 1 -- AST Parser\
Layer 2 -- Control Flow Graph Builder\
Layer 3 -- Null State Propagation Engine\
Layer 4 -- Call Graph Builder\
Layer 5 -- Risk Propagation Engine\
Layer 6 -- Stability Scoring Engine\
Layer 7 -- API Contract Analyzer\
Layer 8 -- Suggestion Engine\
Layer 9 -- Propagation Visualizer

Execution is strictly top-down.

------------------------------------------------------------------------

## 3. Internal Representation (IR Model)

ProjectModel\
├── GlobalCallGraph\
├── PropagationGraph\
├── ProjectRiskSummary\
└── Modules\
└── ModuleModel\
└── PackageModel\
└── ClassModel\
└── MethodModel\
├── ControlFlowModel\
├── NullAnalysisModel\
├── MethodSummary\
├── RiskModel\
├── ContractModel\
├── Suggestions\
└── Issues

IR is immutable after construction.

------------------------------------------------------------------------

## 4. Core Engines

### Null-State Tracking

Domain:

NULL\
NON_NULL\
UNKNOWN

Forward data-flow analysis using worklist fixpoint.

Merge rule:

merge(NULL, NON_NULL) → UNKNOWN\
merge(NULL, UNKNOWN) → UNKNOWN\
merge(NON_NULL, UNKNOWN) → UNKNOWN

------------------------------------------------------------------------

### Call Graph Builder

-   Directed graph
-   Registry-based resolution
-   Conservative inheritance handling
-   Interface-to-implementation mapping

Two-phase build: 1. Method indexing 2. Invocation resolution

------------------------------------------------------------------------

### Risk Propagation

Reverse traversal of call graph.

propagatedRisk(caller) += calleeRisk × decayFactor

Fixpoint iteration with configurable depth limit.

------------------------------------------------------------------------

### Stability Scoring

Aggregates:

-   Intrinsic risk
-   Propagated risk
-   Contract penalties
-   Blast radius

Normalized to 0--100 scale.

------------------------------------------------------------------------

## 5. Architectural Guarantees

-   Deterministic output
-   Immutable method summaries
-   Thread-safe parallel analysis
-   Scalable to 50k+ methods
-   Fully static (no execution)

------------------------------------------------------------------------

## 6. Definition of Done

-   End-to-end pipeline functional
-   Stability Index computed
-   Risk propagation accurate
-   Contract violations detected
-   Suggestions generated
-   JSON export operational
-   CLI + Maven plugin working
-   ≥ 80% unit test coverage

------------------------------------------------------------------------

Architecture Frozen.
