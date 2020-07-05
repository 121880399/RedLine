package org.zzy.lib.redline.detector;

import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.intellij.psi.JavaElementVisitor;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceExpression;

import java.util.Collections;
import java.util.List;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/6/29 16:01
 * 描    述： 枚举检测，最好不要使用枚举类
 * 修订历史：
 * ================================================
 */
public class EnumDetector extends Detector implements Detector.JavaPsiScanner {

    public static final Issue ISSUE = Issue.create(
            "org.zzy.redling.Enum",
            "枚举类检测",
            "检测到使用了枚举类，在android中尽量不要使用枚举类，不仅占内存而且会增加Apk包体积，可以考虑使用注解！",
            Category.PERFORMANCE,
            6,
            Severity.WARNING,
            new Implementation(
                    EnumDetector.class,
                    Scope.JAVA_FILE_SCOPE
            )
    );



    @Override
    public List<Class<? extends PsiElement>> getApplicablePsiTypes() {
        return Collections.singletonList(PsiClass.class);
    }

    @Override
    public JavaElementVisitor createPsiVisitor(JavaContext context) {
        return new JavaElementVisitor() {
            @Override
            public void visitReferenceExpression(PsiReferenceExpression psiReferenceExpression) {

            }

            @Override
            public void visitClass(PsiClass aClass) {
                if(aClass.isEnum()){
                    context.report(ISSUE,aClass,context.getLocation(aClass),"请不要使用枚举类！考虑采用注解替换！");
                }
            }

        };
    }
}
