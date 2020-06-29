package org.zzy.lib.redline.detector;

import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.intellij.psi.JavaElementVisitor;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceExpression;

import java.util.Arrays;
import java.util.List;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/6/29 8:51
 * 描    述：检测代码中是否存在Todo尚未完成
 * 修订历史：android Lint工具没有实现EcjPsiComment，所以
 * visitComment方法不会被回调，此类暂时没法使用
 * ================================================
 */
@Deprecated
public class TodoDetector extends Detector implements  Detector.JavaPsiScanner{

    private static final String TODO = "TODO";

    public static final Issue ISSUE = Issue.create(
            "org.zzy.redline.Todo",
            "Todo检测",
            "代码中还存在Todo尚未完成，请确认！",
            Category.LINT,
            7,
            Severity.ERROR,
            new Implementation(
                    TodoDetector.class,
                    Scope.JAVA_FILE_SCOPE
            )
    );


    @Override
    public List<Class<? extends PsiElement>> getApplicablePsiTypes() {
        return Arrays.asList(PsiComment.class);
    }

    @Override
    public JavaElementVisitor createPsiVisitor(JavaContext context) {
        return new JavaElementVisitor() {

            @Override
            public void visitReferenceExpression(PsiReferenceExpression psiReferenceExpression) {
            }

            @Override
            public void visitComment(PsiComment comment) {
                String text = comment.getText();
                if(text != null && !text.isEmpty()){
                    if(text.contains(TODO)){
                        context.report(ISSUE,comment,context.getLocation(comment),"代码中还存在Todo尚未完成，请确认！");
                    }
                }
            }

        };
    }
}
