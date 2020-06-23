package org.zzy.lib.redline.detector;


import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.intellij.psi.JavaElementVisitor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNewExpression;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/6/21 23:03
 * 描    述：检测是否直接使用了new Message来获取,如果使用
 * 需要改为Message.obtain()
 * 修订历史：该类暂时没有测试通过
 * ================================================
 */
@Deprecated
public class MessageObtainDetector extends Detector implements Detector.UastScanner {

    private static final String MESSAGE="android.os.Message";

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
    public List<Class<? extends PsiElement>> getApplicablePsiTypes() {
        return Arrays.asList(PsiNewExpression.class);
    }


    @Nullable
    @Override
    public JavaElementVisitor createPsiVisitor(@NotNull JavaContext context) {
        return new JavaElementVisitor(){
            @Override
            public void visitNewExpression(PsiNewExpression expression) {
                String qualifyName =expression.getClassReference().getQualifiedName();
                if(qualifyName.equals(MESSAGE)){
                    context.report(
                            ISSUE,
                            expression,
                            context.getLocation(expression),
                            "请使用Message.obtain()来获取Message对象！"
                    );
                }
            }


        };
    }

}
