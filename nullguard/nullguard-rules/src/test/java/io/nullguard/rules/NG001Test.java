package io.nullguard.rules;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import io.nullguard.core.Issue;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NG001Test {

    @Test
    void shouldDetectUnsafeDereference() {
        String code = "class Test { void doWork(Object param) { System.out.println(param.toString()); } }";
        CompilationUnit cu = StaticJavaParser.parse(code);
        List<Issue> issues = new NG001ParameterDereferenceRule().analyze(cu, new File("Test.java"));
        assertEquals(1, issues.size());
        assertEquals("NG001", issues.get(0).getRuleId());
    }

    @Test
    void shouldIgnoreSafeDereference() {
        String code = "class Test { void doWork(Object param) { if(param != null) { System.out.println(param.toString()); } } }";
        CompilationUnit cu = StaticJavaParser.parse(code);
        List<Issue> issues = new NG001ParameterDereferenceRule().analyze(cu, new File("Test.java"));
        assertTrue(issues.isEmpty());
    }

    @Test
    void shouldHonorSuppression() {
        String code = "class Test { @io.nullguard.core.SuppressNullGuard(\"NG001\") void doWork(Object param) { System.out.println(param.toString()); } }";
        CompilationUnit cu = StaticJavaParser.parse(code);
        List<Issue> issues = new NG001ParameterDereferenceRule().analyze(cu, new File("Test.java"));
        assertTrue(issues.isEmpty());
    }
}
