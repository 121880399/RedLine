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
import com.intellij.psi.PsiClassType;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceExpression;

import java.util.Collections;
import java.util.List;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/6/23 10:57
 * 描    述：检测实现了Serializeble类中的内部类是否也同样实现了
 * Serializeble
 * 修订历史：未经测试
 * ================================================
 */
public class SerializableDetector extends Detector implements Detector.JavaPsiScanner{

    private static final String CLASS_SERIALIZABLE = "Serializable";

    public static final Issue ISSUE = Issue.create(
            "InnerClassSerializable",
            "内部类需要实现Serializable接口",
            "内部类需要实现Serializable接口",
            Category.SECURITY, 5, Severity.ERROR,
            new Implementation(SerializableDetector.class, Scope.JAVA_FILE_SCOPE));


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
                PsiClassType[] implementsListTypes = aClass.getImplementsListTypes();
                boolean flag;
                for (PsiClassType type:implementsListTypes) {
                    flag = false;
                    //如果该类实现了Serializable，那么检测它的内部类是否也继承
                    if(type!=null){
                        String className = type.getClassName();
                        if(className.contains(CLASS_SERIALIZABLE)){
                            flag = true;
                            PsiClass[] allInnerClasses = aClass.getInnerClasses();
                            for (PsiClass clazz :allInnerClasses) {
                                if(clazz != null){
                                    PsiClassType[] implementsList = clazz.getImplementsListTypes();
                                    for (PsiClassType impType :implementsList) {
                                        if(impType.getClassName().contains(CLASS_SERIALIZABLE)){
                                            break;
                                        }
                                    }
                                    context.report(ISSUE,aClass,context.getLocation(aClass),aClass.getQualifiedName()+"的内部类"+clazz.getQualifiedName()+"也需要实现Serializable接口!");
                                }
                            }
                        }
                    }
                    if(flag){
                        return;
                    }
                }
            }
        };
    }


}
