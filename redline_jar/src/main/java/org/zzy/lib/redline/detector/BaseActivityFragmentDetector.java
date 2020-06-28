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
 * 创建日期：2020/6/21 22:26
 * 描    述：检测Activity和Fragment是否继承于
 * BaseActivity和BaseFragment
 * 修订历史：由于各家的Base类命名各不相同，所以该检查不会添加到
 * 注册中心，大家如果想使用可以下载源码以后自行添加。
 * ================================================
 */
public class BaseActivityFragmentDetector extends Detector implements Detector.JavaPsiScanner {

    private static final String BASEACTIVITY = "BaseActivity";
    private static final String BASEFRAGMENT = "BaseFragment";

    public static final Issue ISSUE = Issue.create(
            "org.zzy.redline.BaseActivityFragment",
            "Activity或Fragment没有继承指定的父类",
            "Activity或Fragment没有继承指定的父类",
            Category.TYPOGRAPHY,
            8,
            Severity.ERROR,
            new Implementation(BaseActivityFragmentDetector.class, Scope.JAVA_FILE_SCOPE)
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
                String clazzName =  aClass.getName();
                String parentName = aClass.getSuperClass().getName();
                if(clazzName.contains("Activity") && !parentName.equals(BASEACTIVITY)){
                    context.report(ISSUE,
                            aClass,
                            context.getNameLocation(aClass),
                            "Activity没有继承"+BASEACTIVITY);
                    return;
                }

                if(clazzName.contains("Fragment") && !parentName.equals(BASEFRAGMENT)){
                    context.report(ISSUE,
                            aClass,
                            context.getNameLocation(aClass),
                            "Fragment没有继承"+BASEFRAGMENT);
                    return;
                }
            }
        };
    }

}
