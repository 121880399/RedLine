package org.zzy.lib.redline.detector;

import com.android.tools.lint.client.api.UElementHandler;
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
import org.jetbrains.uast.UClassInitializer;
import org.jetbrains.uast.UDeclarationsExpression;
import org.jetbrains.uast.UExpression;

import java.util.Collections;
import java.util.List;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/6/21 23:03
 * 描    述：检测是否直接使用了new Message来获取,如果使用
 * 需要改为Message.obtain()
 * 修订历史：
 * ================================================
 */
public class MessageObtainDetector extends Detector implements Detector.UastScanner {

    public static final Issue ISSUE = Issue.create(
            "org.zzy.redline.MessageObtain",
            "请使用Message.obtain()来获取Message对象！",
            "请使用Message.obtain()来获取Message对象！",
            Category.TYPOGRAPHY,
            6,
            Severity.WARNING,
            new Implementation(
                    MessageObtainDetector.class,
                    Scope.JAVA_FILE_SCOPE
            )
    );

    @Nullable
    @Override
    public List<String> getApplicableConstructorTypes() {
        return Collections.singletonList("android.os.Message");
    }

    @Override
    public void visitConstructor(@NotNull JavaContext context, @NotNull UCallExpression node, @NotNull PsiMethod constructor) {
        context.report(ISSUE,node,context.getLocation(node),"请不要直接使用new Message，改用handle.obtainMessage()或者Message.Obtain()来获取Message对象。");
    }

}
