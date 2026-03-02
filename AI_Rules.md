# NullGuard – AI & Rule Strategy

## 1. Rule Philosophy

NullGuard rules are focused on:

- Runtime crash prevention
- Production stability
- Practical risk detection
- Minimal false positives

Rules must:
- Be deterministic
- Provide line number
- Provide actionable message
- Assign severity

---

## 2. Current Rule Set (MVP)

### NG001 – Parameter Dereference Without Null Check
Severity: HIGH

Detect:
public void process(User user) {
    user.getName();
}

If no null check exists before dereference.

---

### NG002 – Optional.get() Without isPresent()
Severity: HIGH

Detect:
optional.get();

Without:
optional.isPresent()

---

### NG003 – Map.get() Without Null Validation
Severity: MEDIUM

Detect:
map.get(key).toString();

Without null check.

---

### NG004 – Autounboxing Null Risk
Severity: HIGH

Integer x = null;
int y = x;  // possible NPE

---

### NG005 – Field Injection Risk
Severity: LOW

@Autowired
private UserService service;

Suggest constructor injection.

---

## 3. Future AI-Driven Enhancements

### Control Flow Null Tracking

Track:

if(user != null) {
    user.getName(); // safe
}

Implement null state propagation engine.

---

### Data Flow Engine

Track:
- Variable reassignment
- Method returns
- Inter-procedural flows

---

### ML-Based Risk Scoring (Future)

Train model on:
- Real crash logs
- Stack traces
- Production incidents

Use severity weighting model.

---

## 4. Rule Suppression

Support:

@SuppressNullGuard("NG001")

At:
- Method level
- Class level

---

## 5. Community Contribution Guidelines

Each rule must:

- Define ruleId
- Define severity
- Provide test cases
- Include before/after examples
- Include documentation

---

## 6. Severity Model

CRITICAL – Almost guaranteed crash  
HIGH – High probability under common conditions  
MEDIUM – Conditional risk  
LOW – Code smell  

---

## 7. Long-Term Vision

NullGuard becomes:

"Open-source Production Safety Analyzer for Java Microservices"