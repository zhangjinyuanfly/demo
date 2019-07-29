package com.zjy.lintlib;

import com.android.tools.lint.checks.UnusedResourceDetector;
import com.android.tools.lint.client.api.IssueRegistry;
import com.android.tools.lint.detector.api.Issue;

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
            add(LayoutXMLDetector.ISSUE);
            add(ResourceDetector.ISSUE);
        }};
    }

}
