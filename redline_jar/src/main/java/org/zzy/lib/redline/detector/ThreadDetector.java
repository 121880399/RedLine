package org.zzy.lib.redline.detector;

import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.intellij.psi.PsiMethod;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.uast.UCallExpression;

import java.util.Collections;
import java.util.List;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/6/19 9:35
 * 描    述：检测android项目中是否直接使用new Thread创建
 * 线程。
 * 修订历史：
 * ================================================
 */
public class ThreadDetector extends Detector implements Detector.UastScanner {

    public static final Issue ISSUE = Issue.create(
            "org.zzy.redline.newThread",
            "避免直接使用new Thread()创建线程！",
            "请勿直接调用new Thread()创建线程，请使用统一的线程管理工具类。",
            Category.PERFORMANCE,
            6,
            Severity.ERROR,
            new Implementation(
                    ThreadDetector.class,
                    Scope.JAVA_FILE_SCOPE
            )
    );

    @Nullable
    @Override
    public List<String> getApplicableConstructorTypes() {
        return Collections.singletonList("java.lang.Thread");
    }

    @Override
    public void visitConstructor(@NotNull JavaContext context, @NotNull UCallExpression node, @NotNull PsiMethod constructor) {
        if(context.getProject().isAndroidProject()){
            context.report(ISSUE,node,context.getLocation(node),"避免直接创建Thread");
        }
    }
}
