package org.zzy.lib.redline.detector;

import com.android.tools.lint.client.api.UElementHandler;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.intellij.psi.PsiElement;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.uast.UElement;
import org.jetbrains.uast.UField;
import org.jetbrains.uast.ULiteralExpression;
import org.jetbrains.uast.UVariable;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/6/19 18:04
 * 描    述：检查常量是否使用大写
 * 修订历史：
 * ================================================
 */
public class ConstantNameDetector extends Detector implements Detector.UastScanner {
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

    @Nullable
    @Override
    public List<Class<? extends UElement>> getApplicableUastTypes() {
        return Collections.singletonList(UField.class);
    }


    @Nullable
    @Override
    public UElementHandler createUastHandler(@NotNull JavaContext context) {
        return new UElementHandler(){
            @Override
            public void visitField(@NotNull UField node) {
                //如果是static final 修饰，但是没有大写的就提示
                if(node.isFinal() && node.isStatic()){
                    String fieldName = node.getName();
                    if(!fieldName.equals(fieldName.toUpperCase())){
                        context.report(ISSUE,
                                node,
                                context.getLocation(node),
                                "常量名请用大写！"
                                );
                    }
                }
            }
        };
    }
}
