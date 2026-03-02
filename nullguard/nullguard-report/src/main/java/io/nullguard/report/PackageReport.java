package io.nullguard.report;

import io.nullguard.core.Issue;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PackageReport {
    private final String packageName;
    private final Map<String, ClassReport> classes;
    private final Map<Issue.Severity, Long> severityCounts;

    public PackageReport(String packageName, List<Issue> issues) {
        this.packageName = packageName;
        this.classes = issues.stream()
                .collect(Collectors.groupingBy(Issue::getClassName))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> new ClassReport(e.getKey(), e.getValue())));
        this.severityCounts = issues.stream()
                .collect(Collectors.groupingBy(Issue::getSeverity, Collectors.counting()));
    }

    public String getPackageName() { return packageName; }
    public Map<String, ClassReport> getClasses() { return classes; }
    public Map<Issue.Severity, Long> getSeverityCounts() { return severityCounts; }
}
