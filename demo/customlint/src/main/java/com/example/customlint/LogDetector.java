package com.example.customlint;

import com.android.ddmlib.Log;
import com.android.tools.lint.detector.api.*;
import lombok.ast.ForwardingAstVisitor;
import lombok.ast.MethodInvocation;
import lombok.ast.Node;

import java.util.List;

/**
 *
 * lombok.ast : https://github.com/tnorbye/lombok.ast
 * https://projectlombok.org/setup/android
 */
public class LogDetector extends Detector implements Detector.JavaScanner {

    public static Issue issue = Issue.create("uselog", "不要用system.out.","要用log.", Category.SECURITY, 3, Severity.ERROR,
            new Implementation(LogDetector.class, Scope.JAVA_FILE_SCOPE));


    @Override
    public List<Class<? extends Node>> getApplicableNodeTypes() {
        return super.getApplicableNodeTypes();
//        return Collections.<Class<? extends Node>>singletonList(MethodInvocation.class);
    }

    @Override
    public lombok.ast.AstVisitor createJavaVisitor(final JavaContext context) {
        return new ForwardingAstVisitor() {
            @Override
            public boolean visitMethodInvocation(MethodInvocation node) {

                Log.e("zjy","node = "+node.toString());
                context.report(issue, context.getLocation(node), "用错了，赶紧改");
                return super.visitMethodInvocation(node);
            }
        };

    }
}
