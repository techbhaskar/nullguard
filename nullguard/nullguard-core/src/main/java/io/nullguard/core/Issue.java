package io.nullguard.core;

public class Issue {
    public enum Severity {
        CRITICAL, HIGH, MEDIUM, LOW
    }

    private final String ruleId;
    private final Severity severity;
    private final String packageName;
    private final String className;
    private final String methodName;
    private final int lineNumber;
    private final String message;

    public Issue(String ruleId, Severity severity, String packageName, String className, String methodName, int lineNumber, String message) {
        this.ruleId = ruleId;
        this.severity = severity;
        this.packageName = packageName;
        this.className = className;
        this.methodName = methodName;
        this.lineNumber = lineNumber;
        this.message = message;
    }

    public String getRuleId() { return ruleId; }
    public Severity getSeverity() { return severity; }
    public String getPackageName() { return packageName; }
    public String getClassName() { return className; }
    public String getMethodName() { return methodName; }
    public int getLineNumber() { return lineNumber; }
    public String getMessage() { return message; }
}
