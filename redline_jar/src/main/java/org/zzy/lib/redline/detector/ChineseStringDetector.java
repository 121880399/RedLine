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
import org.jetbrains.uast.UExpression;
import org.jetbrains.uast.ULiteralExpression;
import org.jetbrains.uast.UastLiteralUtils;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/6/18 8:55
 * 描    述：检测代码中出现中文的地方，应该统一写到string.xml中
 * 实现JAVAPsiScanner接口可以分析java源文件
 * 修订历史：
 * ================================================
 */
public class ChineseStringDetector extends Detector implements Detector.UastScanner{

    /**
     * 第一步，定义问题
     */
    public static final Issue ISSUE = Issue.create(
            "org.zzy.redline.chineseString",//问题id
            "不要在java文件中直接使用中文",//简短描述
            "不要在java源文件中使用中文，考虑放到string.xml中",//详细解释如何去修复问题
            Category.I18N,//问题的分类
            5,//优先级
            Severity.WARNING,//问题的程度（忽略，提醒，错误）
            new Implementation(//实现类和作用域
                    ChineseStringDetector.class,
                    Scope.JAVA_FILE_SCOPE));

    @Nullable
    @Override
    public List<Class<? extends UElement>> getApplicableUastTypes() {
        return Collections.singletonList(ULiteralExpression.class);
    }

    @Nullable
    @Override
    public UElementHandler createUastHandler(@NotNull JavaContext context) {
        return new UElementHandler(){
            @Override
            public void visitLiteralExpression(@NotNull ULiteralExpression node) {
                String str = UastLiteralUtils.getValueIfStringLiteral(node);
                if(str == null || str.isEmpty()){
                    return;
                }
                //汉字范围
                String patternStr = "[\\u4e00-\\u9fa5]";
                Pattern pattern = Pattern.compile(patternStr);
                Matcher matcher = pattern.matcher(str);
                if(matcher.find()){
                    context.report(ISSUE,node,context.getLocation(node),"Chinese string:"+str);
                }
            }
        };
    }
}
