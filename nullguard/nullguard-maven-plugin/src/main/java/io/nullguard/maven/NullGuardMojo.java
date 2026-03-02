package io.nullguard.maven;

import io.nullguard.core.Issue;
import io.nullguard.core.ProjectScanner;
import io.nullguard.core.RuleEngine;
import io.nullguard.report.HtmlReportGenerator;
import io.nullguard.report.JsonReportGenerator;
import io.nullguard.report.ReportSummary;
import io.nullguard.rules.RuleRegistry;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.util.List;

@Mojo(name = "analyze", defaultPhase = LifecyclePhase.VERIFY)
public class NullGuardMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject project;

    @Parameter(property = "nullguard.failOnCritical", defaultValue = "true")
    private boolean failOnCritical;

    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Starting NullGuard Analysis for " + project.getName());

        File projectDir = project.getBasedir();
        var rules = RuleRegistry.getRules();
        RuleEngine engine = new RuleEngine(rules);
        ProjectScanner scanner = new ProjectScanner(engine);

        List<Issue> issues = scanner.scan(projectDir);
        ReportSummary summary = new ReportSummary(issues, scanner.getParsedFilesCount(), rules.size());

        getLog().info("Files scanned: " + summary.getFilesScanned());
        getLog().info("Total issues found: " + summary.getTotalIssues());
        summary.getSeverityCounts().forEach((seq, count) -> 
            getLog().info(seq + " : " + count)
        );

        File reportDir = new File(project.getBuild().getDirectory(), "nullguard-report");
        try {
            new JsonReportGenerator().generate(summary, new File(reportDir, "report.json"));
            new HtmlReportGenerator().generate(summary, new File(reportDir, "index.html"));
            getLog().info("Reports generated in " + reportDir.getAbsolutePath());
        } catch (Exception e) {
            throw new MojoExecutionException("Error writing reports", e);
        }

        if (failOnCritical) {
            long criticalCount = summary.getSeverityCounts().getOrDefault(Issue.Severity.CRITICAL, 0L);
            if (criticalCount > 0) {
                throw new MojoFailureException("CRITICAL issues found by NullGuard. Failing build.");
            }
        }
    }
}
