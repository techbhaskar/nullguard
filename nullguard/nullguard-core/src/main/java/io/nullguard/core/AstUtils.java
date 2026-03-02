package io.nullguard.core;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.ArrayInitializerExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.nodeTypes.NodeWithAnnotations;

import java.util.Optional;

public class AstUtils {

    public static String getPackageName(CompilationUnit cu) {
        return cu.getPackageDeclaration()
                .map(pd -> pd.getNameAsString())
                .orElse("default");
    }

    public static String getClassName(Node node) {
        Optional<ClassOrInterfaceDeclaration> classOpt = node.findAncestor(ClassOrInterfaceDeclaration.class);
        return classOpt.map(ClassOrInterfaceDeclaration::getNameAsString).orElse("UnknownClass");
    }

    public static String getMethodName(Node node) {
        Optional<MethodDeclaration> methodOpt = node.findAncestor(MethodDeclaration.class);
        return methodOpt.map(MethodDeclaration::getNameAsString).orElse("UnknownMethod");
    }

    public static int getLineNumber(Node node) {
        return node.getRange().map(r -> r.begin.line).orElse(-1);
    }

    public static boolean isSuppressed(Node node, String ruleId) {
        if (node == null) {
            return false;
        }

        Optional<Node> current = Optional.of(node);
        while (current.isPresent()) {
            Node n = current.get();
            if (n instanceof NodeWithAnnotations) {
                NodeWithAnnotations<?> annotatedNode = (NodeWithAnnotations<?>) n;
                for (AnnotationExpr ann : annotatedNode.getAnnotations()) {
                    String annName = ann.getNameAsString();
                    if ("SuppressNullGuard".equals(annName) || "io.nullguard.core.SuppressNullGuard".equals(annName)) {
                        if (ann.isSingleMemberAnnotationExpr()) {
                            Expression val = ann.asSingleMemberAnnotationExpr().getMemberValue();
                            if (matchesValue(val, ruleId)) {
                                return true;
                            }
                        } else if (ann.isNormalAnnotationExpr()) {
                            var pairs = ann.asNormalAnnotationExpr().getPairs();
                            for (var pair : pairs) {
                                if ("value".equals(pair.getNameAsString())) {
                                    if (matchesValue(pair.getValue(), ruleId)) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            current = n.getParentNode();
        }
        return false;
    }

    private static boolean matchesValue(Expression val, String ruleId) {
        if (val.isStringLiteralExpr()) {
            return val.asStringLiteralExpr().getValue().equals(ruleId);
        } else if (val.isArrayInitializerExpr()) {
            ArrayInitializerExpr array = val.asArrayInitializerExpr();
            for (Expression item : array.getValues()) {
                if (item.isStringLiteralExpr() && item.asStringLiteralExpr().getValue().equals(ruleId)) {
                    return true;
                }
            }
        }
        return false;
    }
}
