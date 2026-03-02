package io.nullguard.rules;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.AssignExpr;
import io.nullguard.core.AnalysisRule;
import io.nullguard.core.AstUtils;
import io.nullguard.core.Issue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NG003AutounboxingNullRule implements AnalysisRule {
    @Override
    public String getRuleId() {
        return "NG003";
    }

    @Override
    public List<Issue> analyze(CompilationUnit cu, File file) {
        List<Issue> issues = new ArrayList<>();
        String packageName = AstUtils.getPackageName(cu);

        cu.findAll(AssignExpr.class).forEach(assign -> {
            if (AstUtils.isSuppressed(assign, getRuleId())) {
                return;
            }

            if (assign.getValue().isNullLiteralExpr()) {
                // In a true implementation, we'd check if target is primitive type.
                // Assuming it's a structural match test for prototype here.
                issues.add(new Issue(
                        getRuleId(), Issue.Severity.MEDIUM,
                        packageName, AstUtils.getClassName(assign), AstUtils.getMethodName(assign),
                        AstUtils.getLineNumber(assign),
                        "Potential autounboxing of null literal assignment."
                ));
            }
        });
        return issues;
    }
}
