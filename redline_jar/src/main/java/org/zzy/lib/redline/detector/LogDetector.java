package org.zzy.lib.redline.detector;

import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.intellij.psi.JavaElementVisitor;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiMethodCallExpression;

import java.util.Arrays;
import java.util.List;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/6/18 21:44
 * 描    述：检测在源文件中是否直接使用了系统Log的API
 * 推荐使用前自己封装一层，这样有利于日志打印管理
 * 修订历史：
 * ================================================
 */
public class LogDetector extends Detector implements Detector.JavaPsiScanner {

    public static final Issue ISSUE = Issue.create(
            "org.zzy.redline.Log",
            "不要直接使用系统的Log类",
            "不要直接使用系统的Log类，改用自己封装的Log类！",
            Category.CORRECTNESS,
            6,
            Severity.ERROR,
            new Implementation(LogDetector.class, Scope.JAVA_FILE_SCOPE)
    );

    @Override
    public List<String> getApplicableMethodNames() {
        return Arrays.asList("v", "d", "i", "w", "e");
    }


    @Override
    public void visitMethod(JavaContext context, JavaElementVisitor visitor, PsiMethodCallExpression call, PsiMethod method) {
        if (context.getEvaluator().isMemberInClass(method, "android.util.Log")) {
            context.report(ISSUE, call, context.getLocation(call), "避免调用android.util.Log");
        }
    }
}
