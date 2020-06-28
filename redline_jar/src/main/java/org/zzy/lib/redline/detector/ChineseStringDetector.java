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
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.PsiReferenceExpression;

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
public class ChineseStringDetector extends Detector implements Detector.JavaPsiScanner{

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


    @Override
    public List<Class<? extends PsiElement>> getApplicablePsiTypes() {
        return Collections.singletonList(PsiLiteralExpression.class);
    }

    @Override
    public JavaElementVisitor createPsiVisitor(JavaContext context) {
        return new JavaElementVisitor() {
            @Override
            public void visitReferenceExpression(PsiReferenceExpression psiReferenceExpression) {

            }

            @Override
            public void visitLiteralExpression(PsiLiteralExpression expression) {
                String str = expression.getText();
                if(str == null || str.isEmpty()){
                    return;
                }
                //汉字范围
                String patternStr = "[\\u4e00-\\u9fa5]";
                Pattern pattern = Pattern.compile(patternStr);
                Matcher matcher = pattern.matcher(str);
                if(matcher.find()){
                    context.report(ISSUE,expression,context.getLocation(expression),"Chinese string:"+str);
                }
            }
        };
    }


}
