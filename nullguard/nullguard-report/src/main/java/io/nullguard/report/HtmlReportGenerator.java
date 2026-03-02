package io.nullguard.report;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class HtmlReportGenerator {
    public void generate(ReportSummary summary, File outputFile) throws IOException {
        if (!outputFile.getParentFile().exists()) {
            outputFile.getParentFile().mkdirs();
        }
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write("<html><head><title>NullGuard Report</title></head><body>\n");
            writer.write("<h1>NullGuard Report Summary</h1>\n");
            writer.write("<p>Files Scanned: " + summary.getFilesScanned() + "</p>\n");
            writer.write("<p>Total Issues: " + summary.getTotalIssues() + "</p>\n");
            writer.write("<ul>\n");
            summary.getSeverityCounts().forEach((severity, count) -> {
                try {
                    writer.write("<li>" + severity + ": " + count + "</li>\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            writer.write("</ul>\n");
            
            writer.write("<h2>Issues by Package</h2>\n");
            for (var pkgEntry : summary.getPackages().entrySet()) {
                writer.write("<h3>Package: " + pkgEntry.getKey() + "</h3>\n");
                for (var clsEntry : pkgEntry.getValue().getClasses().entrySet()) {
                    writer.write("<h4>Class: " + clsEntry.getKey() + "</h4>\n");
                    writer.write("<table border='1'><tr><th>Rule</th><th>Severity</th><th>Line</th><th>Message</th></tr>\n");
                    for (var issue : clsEntry.getValue().getIssues()) {
                        writer.write(String.format("<tr><td>%s</td><td>%s</td><td>%d</td><td>%s</td></tr>\n",
                                issue.getRuleId(), issue.getSeverity(), issue.getLineNumber(), issue.getMessage()));
                    }
                    writer.write("</table>\n");
                }
            }
            writer.write("</body></html>\n");
        }
    }
}
