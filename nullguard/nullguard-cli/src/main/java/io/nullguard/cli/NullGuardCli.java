package io.nullguard.cli;

import io.nullguard.core.Issue;
import io.nullguard.core.ProjectScanner;
import io.nullguard.core.RuleEngine;
import io.nullguard.report.HtmlReportGenerator;
import io.nullguard.report.JsonReportGenerator;
import io.nullguard.report.ReportSummary;
import io.nullguard.rules.RuleRegistry;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.util.List;

@Command(name = "nullguard", mixinStandardHelpOptions = true, version = "1.0",
         description = "Scans a Java project for NullPointerException risks.")
public class NullGuardCli implements Runnable {

    @Parameters(index = "0", description = "The directory of the project to scan.")
    private File projectPath;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new NullGuardCli()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        System.out.println("Scanning project... " + projectPath.getAbsolutePath());

        var rules = RuleRegistry.getRules();
        RuleEngine engine = new RuleEngine(rules);
        ProjectScanner scanner = new ProjectScanner(engine);
        
        List<Issue> issues = scanner.scan(projectPath);
        ReportSummary summary = new ReportSummary(issues, scanner.getParsedFilesCount(), rules.size());
        
        System.out.println("Files scanned: " + summary.getFilesScanned());
        System.out.println("Rules applied: " + summary.getRulesApplied());
        System.out.println("Total issues: " + summary.getTotalIssues());

        for (Issue.Severity sev : new Issue.Severity[]{Issue.Severity.CRITICAL, Issue.Severity.HIGH, Issue.Severity.MEDIUM, Issue.Severity.LOW}) {
            long count = summary.getSeverityCounts().getOrDefault(sev, 0L);
            if (count > 0 || sev == Issue.Severity.CRITICAL) { // Just to match structure, or omit 0
                if (count > 0) System.out.println(sev.name() + ": " + count);
            }
        }

        File reportDir = new File(projectPath, "target/nullguard-report");
        try {
            new JsonReportGenerator().generate(summary, new File(reportDir, "report.json"));
            new HtmlReportGenerator().generate(summary, new File(reportDir, "index.html"));
        } catch (Exception e) {
            System.err.println("Failed to generate reports: " + e.getMessage());
        }

        long criticalCount = summary.getSeverityCounts().getOrDefault(Issue.Severity.CRITICAL, 0L);
        long highCount = summary.getSeverityCounts().getOrDefault(Issue.Severity.HIGH, 0L);

        if (criticalCount > 0) {
            System.exit(2);
        } else if (highCount > 0) {
            System.exit(1);
        } else {
            System.exit(0);
        }
    }
}
