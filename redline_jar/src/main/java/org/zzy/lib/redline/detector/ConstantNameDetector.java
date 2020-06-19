package org.zzy.lib.redline.detector;

import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;

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

}
