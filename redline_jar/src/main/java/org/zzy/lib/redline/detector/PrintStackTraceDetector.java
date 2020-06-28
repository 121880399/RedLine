package org.zzy.lib.redline.detector;

import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.intellij.psi.JavaElementVisitor;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiMethodCallExpression;

import java.util.Collections;
import java.util.List;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/6/23 11:05
 * 描    述：检测是否直接调用了Throwable.printStackTrace()
 * 修订历史：
 * ================================================
 */
public class PrintStackTraceDetector extends com.android.tools.lint.detector.api.Detector
        implements com.android.tools.lint.detector.api.Detector.JavaPsiScanner {

    private static final String PRINTSTACKTRACE = "printStackTrace";
    private static final String THROWABLE = "java.lang.Throwable";

    public static final Issue ISSUE = Issue.create(
            "org.zzy.redline.PrintStackTrace",
            "避免直接调用Throwable.printStackTrace()",
            "不要直接使用Throwable.printStackTrace()，使用自定义日志打印替代！",
            Category.LINT, 5, Severity.ERROR,
            new Implementation(PrintStackTraceDetector.class, Scope.JAVA_FILE_SCOPE));


    @Override
    public List<String> getApplicableMethodNames() {
        return Collections.singletonList(PRINTSTACKTRACE);
    }

    @Override
    public void visitMethod(JavaContext context, JavaElementVisitor visitor, PsiMethodCallExpression call, PsiMethod method) {
        if (context.getEvaluator().isMemberInClass(method, THROWABLE)) {
            context.report(ISSUE, call, context.getLocation(call), "不要直接使用Throwable.printStackTrace()，使用自定义日志打印替代！");
        }
    }
}
