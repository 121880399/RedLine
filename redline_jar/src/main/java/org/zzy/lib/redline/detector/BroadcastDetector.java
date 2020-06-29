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
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiMethodCallExpression;
import com.intellij.psi.PsiRecursiveElementVisitor;
import com.intellij.psi.PsiReferenceExpression;
import com.intellij.psi.util.PsiTreeUtil;

import java.util.Collections;
import java.util.List;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/6/29 16:23
 * 描    述：检测动态广播是否被注销
 * 修订历史：测试未通过，无法回调到UnRegisterFinder类的
 * visitMethodCallExpression方法
 * ================================================
 */
@Deprecated
public class BroadcastDetector extends Detector implements Detector.JavaPsiScanner {

    public static final Issue ISSUE = Issue.create(
            "org.zzy.redline.Broadcast",
            "广播没有注销",
            "广播动态注册时，需要在onDestroy中注销。",
            Category.CORRECTNESS,
            5,
            Severity.ERROR,
            new Implementation(
                    BroadcastDetector.class,
                    Scope.JAVA_FILE_SCOPE
            )
    );

    @Override
    public List<String> getApplicableMethodNames() {
        return Collections.singletonList("registerReceiver");
    }

    @Override
    public void visitMethod(JavaContext context, JavaElementVisitor visitor, PsiMethodCallExpression call, PsiMethod method) {
        if(context.getEvaluator().isMemberInClass(method,"android.content.ContextWrapper")){
            //通过调用表达时候得到该表达式所在的类
            PsiClass callClass = PsiTreeUtil.getParentOfType(call, PsiClass.class, true);
            if(callClass!=null){
                //在类中找到onDestroy方法
                PsiMethod[] onDestroys = callClass.findMethodsByName("onDestroy", true);
                UnRegisterFinder finder = new UnRegisterFinder();
                for (PsiMethod everyMethod : onDestroys){
                    everyMethod.accept(finder);
                    if(!finder.isUnRegisterCalled()){
                        context.report(ISSUE,call,context.getLocation(call.getMethodExpression()),"动态注册的广播没有在onDestroy中注销。");
                    }else{
                        super.visitMethod(context,visitor,call,method);
                    }
                }
            }
        }
    }

    private static class UnRegisterFinder extends PsiRecursiveElementVisitor {
        private boolean isFound;

        @Override
        public void visitMethodCallExpression(PsiMethodCallExpression expression) {
            isFound = false;
            PsiReferenceExpression methodExpression = expression.getMethodExpression();
            if("unregisterReceiver".equals(methodExpression.getReferenceName())){
                isFound = true;
            }
            super.visitMethodCallExpression(expression);
        }

        @Override
        public void visitReferenceExpression(PsiReferenceExpression psiReferenceExpression) {

        }


        boolean isUnRegisterCalled(){
            return isFound;
        }
    }
}
