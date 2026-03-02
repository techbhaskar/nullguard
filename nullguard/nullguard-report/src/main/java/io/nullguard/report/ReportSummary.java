package io.nullguard.report;

import io.nullguard.core.Issue;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportSummary {
    private final int totalIssues;
    private final Map<Issue.Severity, Long> severityCounts;
    private final List<Issue> issues;
    private final Map<String, PackageReport> packages;
    private final int filesScanned;
    private final int rulesApplied;

    public ReportSummary(List<Issue> issues, int filesScanned, int rulesApplied) {
        this.issues = issues;
        this.totalIssues = issues.size();
        this.filesScanned = filesScanned;
        this.rulesApplied = rulesApplied;
        this.severityCounts = issues.stream()
            .collect(Collectors.groupingBy(Issue::getSeverity, Collectors.counting()));
        this.packages = issues.stream()
            .collect(Collectors.groupingBy(Issue::getPackageName))
            .entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getKey, e -> new PackageReport(e.getKey(), e.getValue())));
    }

    public int getTotalIssues() { return totalIssues; }
    public Map<Issue.Severity, Long> getSeverityCounts() { return severityCounts; }
    public List<Issue> getIssues() { return issues; }
    public Map<String, PackageReport> getPackages() { return packages; }
    public int getFilesScanned() { return filesScanned; }
    public int getRulesApplied() { return rulesApplied; }
}
