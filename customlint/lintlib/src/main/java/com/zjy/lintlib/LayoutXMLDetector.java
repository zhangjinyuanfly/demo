package com.zjy.lintlib;

import com.android.SdkConstants;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.LayoutDetector;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.android.tools.lint.detector.api.XmlContext;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

import java.util.Arrays;
import java.util.Collection;

public class LayoutXMLDetector extends LayoutDetector {

    public static final Issue ISSUE = Issue.create(
            "ViewIdName",
            "ViewId命名不规范",
            "ViewIdName建议使用 view的缩写加上_xxx,例如tv_username",
            Category.CORRECTNESS, 5, Severity.ERROR,
            new Implementation(LayoutXMLDetector.class, Scope.RESOURCE_FILE_SCOPE));

    @Nullable
    @Override
    public Collection<String> getApplicableElements() {
        return Arrays.asList(
                "TextView"
        );
    }

    @Override
    public void visitElement(@NotNull XmlContext context, @NotNull Element element) {
        Attr attr = element.getAttributeNodeNS(SdkConstants.ANDROID_URI, "id");
        if(attr != null) {
            String value = attr.getValue();
            if(value != null) {
                if (value.startsWith("@+id/") && !value.endsWith("tv")) {
                    context.report(ISSUE, attr, context.getLocation(attr),
                            "TextView 必须以tv结尾");
                }
            }
        }

        Attr attrTextSize = element.getAttributeNodeNS(SdkConstants.ANDROID_URI, "textSize");
        if(attrTextSize != null) {
            String value = attrTextSize.getValue();
            if(value != null) {
                if (!value.toLowerCase().endsWith("sp")) {
                    context.report(ISSUE, attrTextSize, context.getLocation(attrTextSize),
                            "TextView textsize 必须是sp");
                }
            }
        }
    }
}
