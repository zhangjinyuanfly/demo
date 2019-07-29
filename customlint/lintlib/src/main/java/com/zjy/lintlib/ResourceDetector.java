package com.zjy.lintlib;

import com.android.ddmlib.Log;
import com.android.resources.ResourceType;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.android.tools.lint.detector.api.XmlContext;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.uast.UElement;
import org.w3c.dom.Document;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;


public class ResourceDetector extends Detector implements Detector.ResourceFolderScanner {

    public static final Issue ISSUE = Issue.create(
            "FileRem",
            "用错了",
            "Log的应该使用统一Log工具类",
            Category.SECURITY, 5, Severity.ERROR,
            new Implementation(ResourceDetector.class, Scope.RESOURCE_FOLDER_SCOPE));

    @Nullable
    @Override
    public Collection<String> getApplicableElements() {
        return Arrays.asList(
                "drawable"
        );
    }

//    @Override
//    public void visitDocument(@NotNull XmlContext context, @NotNull Document document) {
//        super.visitDocument(context, document);
//    }
//
//    @Override
//    public void visitResourceReference(@NotNull JavaContext context, @NotNull UElement node, @NotNull ResourceType type, @NotNull String name, boolean isFramework) {
//
//        System.out.println("fffff");
//
////        context.report(ISSUE, node, context.getLocation(node),"dfdf");
//    }
}
