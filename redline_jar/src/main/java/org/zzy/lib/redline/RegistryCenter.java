package org.zzy.lib.redline;

import com.android.tools.lint.client.api.IssueRegistry;
import com.android.tools.lint.detector.api.ApiKt;
import com.android.tools.lint.detector.api.Issue;

import org.zzy.lib.redline.detector.ChineseStringDetector;
import org.zzy.lib.redline.detector.ConstantNameDetector;
import org.zzy.lib.redline.detector.LogDetector;
import org.zzy.lib.redline.detector.ThreadDetector;

import java.util.Arrays;
import java.util.List;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/6/18 9:49
 * 描    述：自定义lint注册中心
 * 修订历史：
 * ================================================
 */
public class RegistryCenter extends IssueRegistry {

    @Override
    public List<Issue> getIssues() {
        return Arrays.asList(
                ChineseStringDetector.ISSUE,
                LogDetector.ISSUE,
                ThreadDetector.ISSUE,
                ConstantNameDetector.ISSUE
                );
    }

    @Override
    public int getApi() {
        return ApiKt.CURRENT_API;
    }

    @Override
    public int getMinApi() {
        return 1;
    }
}
