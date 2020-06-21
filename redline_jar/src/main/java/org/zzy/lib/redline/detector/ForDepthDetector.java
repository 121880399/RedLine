package org.zzy.lib.redline.detector;

import com.android.tools.lint.client.api.UElementHandler;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.uast.UElement;
import org.jetbrains.uast.UForEachExpression;
import org.jetbrains.uast.UForExpression;
import org.jetbrains.uast.ULoopExpression;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/6/21 21:49
 * 描    述：For循环嵌套不要超过3层，否则需要优化
 * 修订历史：
 * ================================================
 */
public class ForDepthDetector extends Detector implements Detector.UastScanner {

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

    @Nullable
    @Override
    public List<Class<? extends UElement>> getApplicableUastTypes() {
        return Arrays.asList(UForExpression.class,UForEachExpression.class);
    }

    @Nullable
    @Override
    public UElementHandler createUastHandler(@NotNull JavaContext context) {
        return new UElementHandler(){
            @Override
            public void visitForExpression(@NotNull UForExpression node) {
                int forDepth = getCurrentForDepth(node);
                if(forDepth > 3){
                    context.report(ISSUE,
                            node,
                            context.getLocation(node),
                            "嵌套For循环层数超过3层，请优化代码！");
                }
            }

            @Override
            public void visitForEachExpression(@NotNull UForEachExpression node) {
                int forDepth = getCurrentForDepth(node);
                if(forDepth > 3){
                    context.report(ISSUE,
                            node,
                            context.getLocation(node),
                            "嵌套For循环层数超过3层，请优化代码！");
                }
            }

            /**
            * 不断取父表达式判断是否是For
             * 最后返回当前For表达式的深度
            * 作者: ZhouZhengyi
            * 创建时间: 2020/6/21 22:06
            */
            private int getCurrentForDepth(ULoopExpression node){
                UElement currentCheckNode = node;
                int depth = 0;
                while(currentCheckNode!=null){
                    if(currentCheckNode instanceof UForExpression || currentCheckNode instanceof UForEachExpression){
                        depth++;
                    }
                    currentCheckNode = currentCheckNode.getUastParent();
                }
                return depth;
            }
        };
    }
}
