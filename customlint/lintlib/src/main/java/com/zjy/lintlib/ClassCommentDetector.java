package com.zjy.lintlib;

import com.android.tools.lint.client.api.UElementHandler;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.uast.UClass;
import org.jetbrains.uast.UComment;
import org.jetbrains.uast.UElement;
import org.jetbrains.uast.java.JavaUClass;
import org.jetbrains.uast.kotlin.KotlinUClass;

import java.util.Collections;
import java.util.List;

public class ClassCommentDetector extends Detector implements Detector.UastScanner {


    public static final Issue ISSUE = Issue.create(
            "classcomment",
            "类注释",
            "应该添加必要注释",
            Category.CORRECTNESS,
            5,
            Severity.ERROR,
            new Implementation(ClassCommentDetector.class, Scope.JAVA_FILE_SCOPE)
    );
    @Nullable
    @Override
    public List<Class<? extends UElement>> getApplicableUastTypes() {
        return Collections.singletonList(UClass.class);
    }

    @Nullable
    @Override
    public UElementHandler createUastHandler(@NotNull JavaContext context) {
        return new UElementHandler(){
            @Override
            public void visitClass(@NotNull UClass node) {
                boolean b = (node instanceof JavaUClass || node instanceof KotlinUClass) && node.getContainingClass() == null;
                if(b) {
                    List<UComment> comments = node.getComments();
                    if(comments != null && comments.size() > 0) {
                        UComment uComment = comments.get(0);
                        String s = uComment.asSourceString().toLowerCase();
                        if(!s.contains("author")) {
                            context.report(ISSUE, context.getNameLocation(node), "类注释没有添加author");
                        }
                    } else {
                        context.report(ISSUE, context.getNameLocation(node), "小伙没加注释啊");
                    }

                }
            }
        };
    }
}
