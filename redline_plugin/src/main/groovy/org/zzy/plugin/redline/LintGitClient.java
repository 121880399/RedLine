package org.zzy.plugin.redline;

import com.android.tools.lint.LintCliClient;
import com.android.tools.lint.client.api.LintRequest;

import java.io.File;
import java.util.List;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/7/3 16:30
 * 描    述： 继承LintClient，重写createLintRequest方法
 * 加入git commit时的增量文件
 * 修订历史：
 * ================================================
 */
class LintGitClient extends LintCliClient {


    @Override
    protected LintRequest createLintRequest(List<File> files) {
        LintRequest request = super.createLintRequest(files);
        if(request == null){
            request = new LintRequest(this,files);
        }
        return request;
    }
}
