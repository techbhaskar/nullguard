package io.nullguard.core;

import com.github.javaparser.ast.CompilationUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class RuleEngine {
    private static final Logger LOGGER = LoggerFactory.getLogger(RuleEngine.class);
    private final List<AnalysisRule> rules;

    public RuleEngine(List<AnalysisRule> rules) {
        this.rules = rules;
    }

    public List<Issue> execute(CompilationUnit cu, File file) {
        return rules.stream()
            .flatMap(rule -> {
                try {
                    List<Issue> result = rule.analyze(cu, file);
                    return result == null ? java.util.stream.Stream.empty() : result.stream();
                } catch (Exception e) {
                    LOGGER.warn("Rule {} failed on file {}: {}", rule.getRuleId(), file.getName(), e.getMessage());
                    return java.util.stream.Stream.empty();
                }
            })
            .collect(Collectors.toList());
    }
}
