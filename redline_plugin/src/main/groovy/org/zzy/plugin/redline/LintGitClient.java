package org.zzy.plugin.redline;

import com.android.SdkConstants;
import com.android.annotations.NonNull;
import com.android.builder.model.AndroidLibrary;
import com.android.builder.model.AndroidProject;
import com.android.builder.model.Variant;
import com.android.tools.lint.LintCliClient;
import com.android.tools.lint.client.api.CircularDependencyException;
import com.android.tools.lint.client.api.LintRequest;
import com.android.tools.lint.detector.api.LintUtils;
import com.android.tools.lint.detector.api.Project;
import com.google.common.collect.Lists;

import org.gradle.api.plugins.ExtraPropertiesExtension;
import org.gradle.tooling.provider.model.ToolingModelBuilder;
import org.gradle.tooling.provider.model.ToolingModelBuilderRegistry;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

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

    public List<File> customViewRuleJars = Lists.newArrayList();

    @Override
    protected LintRequest createLintRequest(List<File> files) {
        LintRequest request = super.createLintRequest(files);
        if (request == null) {
            request = new LintRequest(this,files);
        }
        return request;
    }

    public void setCustomViewRuleJars(List<File> files){
        this.customViewRuleJars.addAll(files);
    }


    @Override
    public List<File> findRuleJars(Project project) {
        if (project.isGradleProject()) {
            if (project.isLibrary()) {
                AndroidLibrary model = project.getGradleLibraryModel();
                if (model != null) {
                    File lintJar = model.getLintJar();
                    if (lintJar.exists()) {
                        return Collections.singletonList(lintJar);
                    }
                }
            } else if (project.getSubset() != null) {
                return customViewRuleJars;
            } else if (project.getDir().getPath().endsWith(SdkConstants.DOT_AAR)) {
                File lintJar = new File(project.getDir(), "lint.jar");
                if (lintJar.exists()) {
                    return Collections.singletonList(lintJar);
                }
            }
        }

        return Collections.emptyList();
    }

}
