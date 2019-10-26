package com.zjy.lintlib;

import com.android.tools.lint.detector.api.Detector;

import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;


public class ResourceDetector extends Detector implements Detector.ResourceFolderScanner {

//    public static final Issue ISSUE = Issue.create(
//            "FileRem",
//            "用错了",
//            "Log的应该使用统一Log工具类",
//            Category.SECURITY, 5, Severity.ERROR,
//            new Implementation(ResourceDetector.class, Scope.RESOURCE_FOLDER_SCOPE));

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
