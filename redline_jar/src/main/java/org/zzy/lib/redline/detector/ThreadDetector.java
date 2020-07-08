package org.zzy.lib.redline.detector;

import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.intellij.psi.JavaElementVisitor;
import com.intellij.psi.PsiJavaCodeReferenceElement;
import com.intellij.psi.PsiNewExpression;
import com.intellij.psi.PsiReferenceExpression;

import java.util.Arrays;
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
public class ThreadDetector extends Detector implements Detector.JavaPsiScanner {

    private static final String THREAD="Thread";

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

    @Override
    public List<Class<? extends com.intellij.psi.PsiElement>> getApplicablePsiTypes() {
        return Arrays.asList(PsiNewExpression.class);
    }


    @Override
    public JavaElementVisitor createPsiVisitor(JavaContext context) {
        return new JavaElementVisitor() {
            @Override
            public void visitReferenceExpression(PsiReferenceExpression psiReferenceExpression) {
            }

            @Override
            public void visitNewExpression(PsiNewExpression expression) {
                PsiJavaCodeReferenceElement classReference = expression.getClassReference();
                if(classReference != null){
                    String qualifyName =classReference.getQualifiedName();
                    if(qualifyName!=null && qualifyName.equals(THREAD)){
                        context.report(
                                ISSUE,
                                expression,
                                context.getLocation(expression),
                                "避免直接使用new Thread()创建线程！"
                        );
                    }
                }
            }
        };
    }
}
