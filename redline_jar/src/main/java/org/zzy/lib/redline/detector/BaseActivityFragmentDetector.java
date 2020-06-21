package org.zzy.lib.redline.detector;

import com.android.tools.lint.client.api.UElementHandler;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.intellij.psi.PsiReferenceList;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.uast.UClass;
import org.jetbrains.uast.UElement;

import java.util.Collections;
import java.util.List;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/6/21 22:26
 * 描    述：检测Activity和Fragment是否继承于
 * BaseActivity和BaseFragment
 * 修订历史：
 * ================================================
 */
public class BaseActivityFragmentDetector extends Detector implements Detector.UastScanner {

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

    @Nullable
    @Override
    public List<Class<? extends UElement>> getApplicableUastTypes() {
        return Collections.singletonList(UClass.class);
    }

    @Nullable
    @Override
    public UElementHandler createUastHandler(@NotNull JavaContext context) {
        return new UElementHandler(){

            @Override
            public void visitClass(@NotNull UClass node) {
               String clazzName =  node.getName();
                String parentName = node.getUastParent().getClass().getName();
               if(clazzName.contains("Activity") && !parentName.equals(BASEACTIVITY)){
                    context.report(ISSUE,
                            node,
                            context.getNameLocation(node),
                            "Activity没有继承"+BASEACTIVITY);
                    return;
               }

               if(clazzName.contains("Fragment") && !parentName.equals(BASEFRAGMENT)){
                   context.report(ISSUE,
                           node,
                           context.getNameLocation(node),
                           "Fragment没有继承"+BASEFRAGMENT);
                   return;
               }
            }
        };
    }
}
