package io.nullguard.report;

import io.nullguard.core.Issue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ClassReport {
    private final String className;
    private final List<Issue> issues;
    private final Map<Issue.Severity, Long> severityCounts;

    public ClassReport(String className, List<Issue> issues) {
        this.className = className;
        this.issues = new ArrayList<>(issues);
        this.severityCounts = issues.stream()
                .collect(Collectors.groupingBy(Issue::getSeverity, Collectors.counting()));
    }

    public String getClassName() { return className; }
    public List<Issue> getIssues() { return issues; }
    public Map<Issue.Severity, Long> getSeverityCounts() { return severityCounts; }
}
