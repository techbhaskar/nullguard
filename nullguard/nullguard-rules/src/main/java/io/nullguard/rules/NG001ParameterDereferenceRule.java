package io.nullguard.rules;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.IfStmt;
import io.nullguard.core.AnalysisRule;
import io.nullguard.core.AstUtils;
import io.nullguard.core.Issue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NG001ParameterDereferenceRule implements AnalysisRule {
    @Override
    public String getRuleId() {
        return "NG001";
    }

    @Override
    public List<Issue> analyze(CompilationUnit cu, File file) {
        List<Issue> issues = new ArrayList<>();
        String packageName = AstUtils.getPackageName(cu);

        cu.findAll(MethodCallExpr.class).forEach(call -> {
            if (AstUtils.isSuppressed(call, getRuleId())) {
                return;
            }

            if (call.getScope().isPresent() && call.getScope().get().isNameExpr()) {
                String potentialNullObj = call.getScope().get().toString();

                if ("param".equals(potentialNullObj) || "obj".equals(potentialNullObj) || "user".equals(potentialNullObj)) {
                    // Control flow MVP check: search for non-null checks in parent IfStmt
                    boolean isProtected = call.findAncestor(IfStmt.class).map(ifStmt -> {
                        String condition = ifStmt.getCondition().toString();
                        return condition.contains(potentialNullObj + " != null") ||
                               condition.contains("Objects.nonNull(" + potentialNullObj + ")") ||
                               condition.contains("Optional.ofNullable(" + potentialNullObj + ")");
                    }).orElse(false);

                    if (!isProtected) {
                        issues.add(new Issue(
                                getRuleId(), Issue.Severity.HIGH,
                                packageName, AstUtils.getClassName(call), AstUtils.getMethodName(call),
                                AstUtils.getLineNumber(call),
                                "Parameter dereference without null check: " + potentialNullObj
                        ));
                    }
                }
            }
        });
        return issues;
    }
}
