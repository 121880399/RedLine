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
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiExpressionList;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiMethodCallExpression;
import com.intellij.psi.PsiReferenceExpression;

import java.util.Arrays;
import java.util.List;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/6/19 17:36
 * 描    述：检测Activity和Fragment中的布局是否以
 * activity_和fragment_开头。
 * 比如:activity_main.xml fragment_main.xml
 * 做法：
 * 通过判断在调用setContentView和inflate方法时的布局名称
 * 修订历史：
 * ================================================
 */
public class ActivityFragmentLayoutNameDetector extends Detector implements Detector.JavaPsiScanner {

    private static final String SETCONTENTVIEW = "setContentView";
    private static final String INFLATE = "inflate";
    private static final String ACTIVITYPREFIX = "activity_";
    private static final String FRAGMENTPREFIX = "fragment_";

    private static final String ANDROIDX_APPCOMPATACTIVITY = "androidx.appcompat.app.AppCompatActivity";
    private static final String V7_APPCOMPATACTIVITY = "android.support.v7.app.AppCompatActivity";
    private static final String V4_FRAGMENT = "android.support.v4.app.Fragment";
    private static final String ANDROIDX_FRAGMENT = "androidx.fragment.app.Fragment";
    private static final String APP_FRAGMENT = "android.app.Fragment";

    public static final Issue ACTIVITY_LAYOUT_PREFIX_ISSUE=Issue.create(
            "org.zzy.redline.ActivityLayoutPrefix",
            "Activity布局前缀名应该以activity_开头",
            "Activity布局前缀名应该以activity_开头",
            Category.TYPOGRAPHY,
            7,
            Severity.WARNING,
            new Implementation(
                    ActivityFragmentLayoutNameDetector.class,
                    Scope.JAVA_FILE_SCOPE
            )
    );

    public static final Issue FRAGMENT_LAYOUT_PREFIX_ISSUE=Issue.create(
            "org.zzy.redline.ActivityLayoutPrefix",
            "Fragment布局前缀名应该以fragment_开头",
            "Fragment布局前缀名应该以fragment_开头",
            Category.TYPOGRAPHY,
            7,
            Severity.WARNING,
            new Implementation(
                    ActivityFragmentLayoutNameDetector.class,
                    Scope.JAVA_FILE_SCOPE
            )
    );

    @Override
    public List<String> getApplicableMethodNames() {
        return Arrays.asList(SETCONTENTVIEW,INFLATE);
    }


    /**
     * @param context
     * @param visitor
     * @param call 调用方法
     * @param method 定义方法
     */
    @Override
    public void visitMethod(JavaContext context, JavaElementVisitor visitor, PsiMethodCallExpression call, PsiMethod method) {
        PsiClass containingClass = method.getContainingClass();
        String qualifiedName = containingClass.getQualifiedName();
        if(qualifiedName == null || qualifiedName.isEmpty()){
            return;
        }
        boolean isActivity = false;
        boolean isFragment = false;
        if(qualifiedName.equals(ANDROIDX_APPCOMPATACTIVITY) || qualifiedName.equals(V7_APPCOMPATACTIVITY)){
           isActivity=true;
        }

        if(qualifiedName.equals(V4_FRAGMENT) || qualifiedName.equals(ANDROIDX_FRAGMENT) || qualifiedName.equals(APP_FRAGMENT)){
            isFragment = true;
        }
        if(!isActivity && !isFragment){
            return;
        }
        PsiExpressionList argumentList = call.getArgumentList();
        if(argumentList!=null){
            PsiExpression[] expressions = argumentList.getExpressions();
            if(expressions!=null && expressions.length > 0){
                PsiReferenceExpression layoutID = (PsiReferenceExpression) expressions[0];
                if(layoutID!=null){
                    String referenceName = layoutID.getReferenceName();
                    if(referenceName!=null && !referenceName.isEmpty()){
                        if(isActivity && !layoutXmlIsStartWithPrefix(referenceName,ACTIVITYPREFIX)){
                            context.report(ACTIVITY_LAYOUT_PREFIX_ISSUE,call,context.getLocation(call),"Activity布局前缀名应该以activity_开头");
                            return;
                        }
                        if(isFragment && !layoutXmlIsStartWithPrefix(referenceName,FRAGMENTPREFIX)){
                            context.report(FRAGMENT_LAYOUT_PREFIX_ISSUE,call,context.getLocation(call),"Fragment布局前缀名应该以fragment_开头");
                            return;
                        }
                    }
                }
            }
        }
    }

    /**
    * 判断布局xml文件是否以指定前缀开头
    * 作者: ZhouZhengyi
    * 创建时间: 2020/6/28 21:51
    */
    private boolean layoutXmlIsStartWithPrefix(String layoutString,String prefix){
        return layoutString.startsWith(prefix);
    }
}
