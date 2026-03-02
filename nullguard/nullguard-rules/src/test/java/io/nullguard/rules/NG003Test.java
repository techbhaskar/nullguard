package io.nullguard.rules;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import io.nullguard.core.Issue;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NG003Test {

    @Test
    void shouldDetectAutounboxingNull() {
        String code = "class Test { void doWork() { int a; a = null; } }";
        CompilationUnit cu = StaticJavaParser.parse(code);
        List<Issue> issues = new NG003AutounboxingNullRule().analyze(cu, new File("Test.java"));
        assertEquals(1, issues.size());
        assertEquals("NG003", issues.get(0).getRuleId());
    }

    @Test
    void shouldHonorSuppression() {
        String code = "class Test { @io.nullguard.core.SuppressNullGuard(\"NG003\") void doWork() { int a; a = null; } }";
        CompilationUnit cu = StaticJavaParser.parse(code);
        List<Issue> issues = new NG003AutounboxingNullRule().analyze(cu, new File("Test.java"));
        assertTrue(issues.isEmpty());
    }
}
