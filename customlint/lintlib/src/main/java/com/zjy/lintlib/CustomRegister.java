package com.zjy.lintlib;

import com.android.tools.lint.checks.UnusedResourceDetector;
import com.android.tools.lint.client.api.IssueRegistry;
import com.android.tools.lint.detector.api.ApiKt;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.LayoutDetector;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CustomRegister extends IssueRegistry {

    @NotNull
    @Override
    public List<Issue> getIssues() {
        return new ArrayList<Issue>(){{
            add(MethodDetector.ISSUE);
            add(ConstructorDetector.ISSUE);
            add(UnusedResourceDetector.ISSUE);
        }};
    }

    @Override
    public int getApi() {
        return ApiKt.CURRENT_API;
    }

    @Override
    public int getMinApi() {
        return 1;
    }
}
