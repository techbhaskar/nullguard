package io.nullguard.rules;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.IfStmt;
import io.nullguard.core.AnalysisRule;
import io.nullguard.core.AstUtils;
import io.nullguard.core.Issue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NG002OptionalGetRule implements AnalysisRule {
    @Override
    public String getRuleId() {
        return "NG002";
    }

    @Override
    public List<Issue> analyze(CompilationUnit cu, File file) {
        List<Issue> issues = new ArrayList<>();
        String packageName = AstUtils.getPackageName(cu);

        cu.findAll(MethodCallExpr.class).forEach(call -> {
            if (AstUtils.isSuppressed(call, getRuleId())) {
                return;
            }

            if ("get".equals(call.getNameAsString()) && call.getArguments().isEmpty()) {
                boolean isProtected = false;
                if (call.getScope().isPresent()) {
                    Expression scope = call.getScope().get();
                    String optName = scope.toString();
                    
                    isProtected = call.findAncestor(IfStmt.class).map(ifStmt -> {
                        String condition = ifStmt.getCondition().toString();
                        return condition.contains(optName + ".isPresent()") || 
                               condition.contains("!" + optName + ".isEmpty()");
                    }).orElse(false);
                }

                if (!isProtected) {
                    issues.add(new Issue(
                            getRuleId(), Issue.Severity.CRITICAL,
                            packageName, AstUtils.getClassName(call), AstUtils.getMethodName(call),
                            AstUtils.getLineNumber(call),
                            "Optional.get() called without isPresent() check."
                    ));
                }
            }
        });
        return issues;
    }
}
