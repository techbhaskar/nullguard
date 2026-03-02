package io.nullguard.core;

import com.github.javaparser.ast.CompilationUnit;
import java.io.File;
import java.util.List;

public interface AnalysisRule {
    String getRuleId();
    List<Issue> analyze(CompilationUnit cu, File file);
}
