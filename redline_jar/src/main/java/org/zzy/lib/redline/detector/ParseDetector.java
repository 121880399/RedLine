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
import com.intellij.psi.PsiTryStatement;
import com.intellij.psi.util.PsiTreeUtil;

import java.util.Arrays;
import java.util.List;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/6/29 15:38
 * 描    述：检测parse类型的方法是否添加了try catch
 * 修订历史：
 * ================================================
 */
public class ParseDetector extends Detector implements Detector.JavaPsiScanner {

    public static final Issue ISSUE = Issue.create(
            "org.zzy.redling.Parse",
            "parse方法检测",
            "parse方法需要添加try catch语句",
            Category.SECURITY,
            7,
            Severity.ERROR,
            new Implementation(
                    ParseDetector.class,
                    Scope.JAVA_FILE_SCOPE
            )
    );

    @Override
    public List<String> getApplicableMethodNames() {
        return Arrays.asList("parseColor","parseInt","parseDouble","parseFloat");
    }

    @Override
    public void visitMethod(JavaContext context, JavaElementVisitor visitor, PsiMethodCallExpression call, PsiMethod method) {
        boolean isColor = context.getEvaluator().isMemberInClass(method, "android.graphics.Color");
        boolean isInteger = context.getEvaluator().isMemberInClass(method, "java.lang.Integer");
        boolean isDouble = context.getEvaluator().isMemberInClass(method, "java.lang.Double");
        boolean isFloat = context.getEvaluator().isMemberInClass(method, "java.lang.Float");

        if (!isColor && !isInteger && !isDouble && !isFloat) {
            return;
        }

        PsiTryStatement tryStatement = PsiTreeUtil.getParentOfType(call, PsiTryStatement.class, true);
        if(tryStatement == null){
            context.report(ISSUE,call,context.getLocation(call),"parse方法没有添加try catch语句");
        }
    }
}
