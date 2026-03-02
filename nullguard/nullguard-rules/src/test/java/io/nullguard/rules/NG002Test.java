package io.nullguard.rules;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import io.nullguard.core.Issue;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NG002Test {

    @Test
    void shouldDetectUnsafeOptionalGet() {
        String code = "class Test { void doWork(java.util.Optional<String> opt) { System.out.println(opt.get()); } }";
        CompilationUnit cu = StaticJavaParser.parse(code);
        List<Issue> issues = new NG002OptionalGetRule().analyze(cu, new File("Test.java"));
        assertEquals(1, issues.size());
        assertEquals("NG002", issues.get(0).getRuleId());
        assertEquals(Issue.Severity.CRITICAL, issues.get(0).getSeverity());
    }

    @Test
    void shouldIgnoreSafeOptionalGet() {
        String code = "class Test { void doWork(java.util.Optional<String> opt) { if(opt.isPresent()) { System.out.println(opt.get()); } } }";
        CompilationUnit cu = StaticJavaParser.parse(code);
        List<Issue> issues = new NG002OptionalGetRule().analyze(cu, new File("Test.java"));
        assertTrue(issues.isEmpty());
    }

    @Test
    void shouldHonorSuppression() {
        String code = "class Test { @io.nullguard.core.SuppressNullGuard(\"NG002\") void doWork(java.util.Optional<String> opt) { System.out.println(opt.get()); } }";
        CompilationUnit cu = StaticJavaParser.parse(code);
        List<Issue> issues = new NG002OptionalGetRule().analyze(cu, new File("Test.java"));
        assertTrue(issues.isEmpty());
    }
}
