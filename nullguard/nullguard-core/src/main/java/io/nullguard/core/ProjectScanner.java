package io.nullguard.core;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProjectScanner {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectScanner.class);
    private final RuleEngine ruleEngine;
    private final ForkJoinPool forkJoinPool;
    private final AtomicInteger parsedFilesCount = new AtomicInteger(0);

    public ProjectScanner(RuleEngine ruleEngine) {
        this.ruleEngine = ruleEngine;
        this.forkJoinPool = new ForkJoinPool();
    }

    public List<Issue> scan(File projectDir) {
        List<Issue> allIssues = new ArrayList<>();
        parsedFilesCount.set(0);

        if (!projectDir.exists() || !projectDir.isDirectory()) {
            LOGGER.error("Project directory does not exist: {}", projectDir.getAbsolutePath());
            return allIssues;
        }

        try (Stream<Path> paths = Files.walk(projectDir.toPath())) {
            List<File> javaFiles = paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .map(Path::toFile)
                    .collect(Collectors.toList());

            allIssues = forkJoinPool.submit(() ->
                    javaFiles.parallelStream()
                            .flatMap(file -> {
                                try {
                                    CompilationUnit cu = StaticJavaParser.parse(file);
                                    parsedFilesCount.incrementAndGet();
                                    return ruleEngine.execute(cu, file).stream();
                                } catch (Exception e) {
                                    LOGGER.warn("Failed to parse file: {}", file.getAbsolutePath());
                                    return Stream.empty();
                                }
                            })
                            .collect(Collectors.toList())
            ).join();

        } catch (Exception e) {
            LOGGER.error("Failed to scan project directory", e);
        }
        
        return allIssues;
    }

    public int getParsedFilesCount() {
        return parsedFilesCount.get();
    }
}
