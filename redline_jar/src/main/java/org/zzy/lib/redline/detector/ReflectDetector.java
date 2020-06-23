package org.zzy.lib.redline.detector;

import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethodCallExpression;
import com.intellij.psi.PsiReferenceExpression;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.uast.UReferenceExpression;

import java.util.Arrays;
import java.util.List;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/6/23 11:10
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class ReflectDetector extends Detector implements Detector.UastScanner {
    public static final Issue ISSUE = Issue.create(
            "Reflect",
            "反射注意防混淆",
            "反射操作的类，请添加混淆规则，反射调用放入try...catch块中",
            Category.LINT, 5, Severity.ERROR,
            new Implementation(ReflectDetector.class, Scope.JAVA_FILE_SCOPE));


    @Nullable
    @Override
    public List<String> getApplicableReferenceNames() {
        return Arrays.asList(
                "ReflectUtil",
                "ReflectUtils"
        );
    }

    @Override
    public void visitReference(@NotNull JavaContext context,
                               @NotNull UReferenceExpression reference,
                               @NotNull PsiElement referenced) {
        PsiElement node = reference.getJavaPsi();
        if (!(node instanceof PsiReferenceExpression)) {
            return;
        }

        node = node.getParent();
        if (!(node instanceof PsiReferenceExpression)) {
            return;
        }

        node = node.getParent();
        if (!(node instanceof PsiMethodCallExpression)) {
            return;
        }

        context.report(ISSUE,
                node,
                context.getLocation(node),
                "反射操作的类，请添加混淆规则，反射调用放入try...catch块中");
    }
}
