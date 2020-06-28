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
import com.intellij.psi.PsiForStatement;
import com.intellij.psi.PsiForeachStatement;
import com.intellij.psi.PsiLoopStatement;
import com.intellij.psi.PsiReferenceExpression;

import java.util.Arrays;
import java.util.List;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/6/21 21:49
 * 描    述：For循环嵌套不要超过3层，否则需要优化
 * 修订历史：
 * ================================================
 */
public class ForDepthDetector extends Detector implements Detector.JavaPsiScanner {

    public static final Issue ISSUE = Issue.create(
      "org.zzy.redline.ForDepth",
      "For循环深度不要超过3层",
      "For循环嵌套不要超过3层，请检查代码并且进行优化!",
            Category.PERFORMANCE,
            8,
            Severity.ERROR,
            new Implementation(ForDepthDetector.class,
                    Scope.JAVA_FILE_SCOPE)
    );

    @Override
    public List<Class<? extends PsiElement>> getApplicablePsiTypes() {
        return Arrays.asList(PsiForStatement.class,PsiForeachStatement.class);
    }

    @Override
    public JavaElementVisitor createPsiVisitor(JavaContext context) {
        return new JavaElementVisitor() {
            @Override
            public void visitReferenceExpression(PsiReferenceExpression psiReferenceExpression) {

            }

            @Override
            public void visitForStatement(PsiForStatement statement) {
                int forDepth = getCurrentForDepth(statement);
                if(forDepth > 3){
                    context.report(ISSUE,
                            statement,
                            context.getLocation(statement),
                            "嵌套For循环层数超过3层，请优化代码！");
                }
            }

            @Override
            public void visitForeachStatement(PsiForeachStatement statement) {
                int forDepth = getCurrentForDepth(statement);
                if(forDepth > 3){
                    context.report(ISSUE,
                            statement,
                            context.getLocation(statement),
                            "嵌套For循环层数超过3层，请优化代码！");
                }
            }


            private int getCurrentForDepth(PsiLoopStatement node){
                PsiElement currentCheckNode = node;
                int depth = 0;
                while(currentCheckNode!=null){
                    if(currentCheckNode instanceof PsiForStatement || currentCheckNode instanceof PsiForeachStatement){
                        depth++;
                    }
                    currentCheckNode = currentCheckNode.getParent();
                }
                return depth;
            }
        };
    }


}
