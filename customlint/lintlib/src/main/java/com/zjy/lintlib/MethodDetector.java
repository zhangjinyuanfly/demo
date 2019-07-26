package com.zjy.lintlib;

import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.intellij.psi.PsiMethod;

import org.jetbrains.uast.UCallExpression;

import java.util.Arrays;
import java.util.List;

public class MethodDetector extends Detector implements Detector.UastScanner {

    public static final Issue ISSUE = Issue.create(
            "LogUsage",
            "用错了",
            "Log的应该使用统一Log工具类",
            Category.SECURITY, 5, Severity.ERROR,
            new Implementation(MethodDetector.class, Scope.JAVA_FILE_SCOPE));


    @Override
    public List<String> getApplicableMethodNames() {
        return Arrays.asList("v", "d", "i", "w", "e");
    }

    @Override
    public void visitMethod(JavaContext context, UCallExpression node, PsiMethod method) {
        if (context.getEvaluator().isMemberInClass(method, "android.util.Log")) {
            context.report(ISSUE, node, context.getLocation(node), "改一下应该使用统一Log工具类");
        }
    }

}
