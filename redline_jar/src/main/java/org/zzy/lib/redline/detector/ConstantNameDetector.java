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
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiIdentifier;
import com.intellij.psi.PsiReferenceExpression;

import java.util.Collections;
import java.util.List;


/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/6/19 18:04
 * 描    述：检查常量是否使用大写，不检测R文件
 * 修订历史：
 * ================================================
 */
public class ConstantNameDetector extends Detector implements Detector.JavaPsiScanner {
    private static final String STATIC = "static";
    private static final String FINAL = "final";
    private static final String RFILE = "R";
    public static final Issue ISSUE = Issue.create(
            "org.zzy.redline.Constant",
            "常量请使用大写",
            "字符串常量请使用大写！",
            Category.TYPOGRAPHY,
            5,
            Severity.WARNING,
            new Implementation(
                    ConstantNameDetector.class,
                    Scope.JAVA_FILE_SCOPE)
    );

    @Override
    public List<Class<? extends PsiElement>> getApplicablePsiTypes() {
        return Collections.singletonList(PsiField.class);
    }

    @Override
    public JavaElementVisitor createPsiVisitor(JavaContext context) {
        return new JavaElementVisitor() {
            @Override
            public void visitReferenceExpression(PsiReferenceExpression psiReferenceExpression) {

            }

            @Override
            public void visitField(PsiField field) {
                //如果是static final 修饰，但是没有大写的就提示
                PsiClass containingClass = field.getContainingClass();
                while (containingClass!=null) {
                    String clazzName = containingClass.getName();
                    if (clazzName == null || clazzName.isEmpty()) {
                        return;
                    }
                    if (clazzName.equals(RFILE)) {
                        return;
                    }
                    containingClass = containingClass.getContainingClass();
                }
                String text = field.getText();
                if (text != null && text.contains(STATIC) && text.contains(FINAL)) {
                    PsiIdentifier nameIdentifier = field.getNameIdentifier();
                    if(nameIdentifier == null){
                        return;
                    }
                    String name = nameIdentifier.getText();
                    if(name == null || name.isEmpty()){
                        return;
                    }
                    if(name.equals(name.toUpperCase())){
                        return;
                    }
                    context.report(ISSUE,
                            field,
                            context.getLocation(field),
                            "常量名请用大写！"
                    );
                }
            }
        };
    }
}
