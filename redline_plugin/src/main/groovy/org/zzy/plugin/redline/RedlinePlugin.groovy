package org.zzy.plugin.redline

import com.android.tools.lint.HtmlReporter
import com.android.tools.lint.Reporter
import com.android.tools.lint.checks.BuiltinIssueRegistry
import com.google.common.collect.Lists
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.DependencySet

import static java.io.File.separator
import static java.io.File.separatorChar

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/7/3 16:19
 * 描    述：
 * 修订历史：
 * ================================================
 */
class RedlinePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.task("incrementLint").doLast{
            println("=========== IncrementLint  start ==============")
            //1.得到需要检查的文件列表
            List<String> allFileName = getCommitChange(project)
            List<File> allFiles = new ArrayList<>()
            File file
            for(String fileName : allFileName){
                println("file path:" + fileName)
                file = new File(fileName)
                allFiles.add(file)
            }
            println("need cheched files size:"+allFiles.size())
            println(System.getenv("ANDROID_HOME"))
            def lintGitClient = new LintGitClient()
            lintGitClient.setCustomViewRuleJars(getLintJar(project))
            def flag = lintGitClient.flags
            flag.setExitCode = true

            //2.设置输出报告
            File reportFile = new File(project.rootDir,"lint-check-result.html")
            Reporter reporter = new HtmlReporter(lintGitClient,reportFile,flag)
            flag.reporters.add(reporter)
            //3.开始lint检查,BuiltinIssueRegistry 系统定义的问题集
            lintGitClient.run(new BuiltinIssueRegistry(),allFiles)
        }

        //根据不同系统将脚本赋值到.git/hooks/文件夹下
        project.task("copyToGitHooks").doLast {
            File postCommit
            String OSType = System.getProperty("os.name")
            println("OS type:"+OSType)
            if(OSType.toLowerCase().contains("windows")){
                postCommit = new File(project.rootDir,"pre-commit-windows")
            }else{
                postCommit = new File(project.rootDir,"pre-commit")
            }

            project.copy {
                from (postCommit){
                    rename{
                        String fileName -> "pre-commit"
                    }
                }
                into new File(project.rootDir,".git/hooks/")
            }
            //非Windows操作系统添加权限
            if(!OSType.toLowerCase().contains("windows")){
                Runtime.getRuntime().exec("chmod -R +x .git/hooks/")
            }
        }

    }

    /**
     * 遍历所有Project，找出自定义Lint.jar
     * */
    static List<File> getLintJar(Project project){
        def allprojects = project.getRootProject().getChildProjects()
        List<File> customRuleJars = Lists.newArrayList()
        for (Map.Entry<String, Project>  entrySet: allprojects.entrySet()){
            File appLintJar = new File(entrySet.getValue().getBuildDir(),
                    "intermediates"+separatorChar+"lint" + separatorChar + "lint.jar")
            if (appLintJar.exists()) {
                customRuleJars.add(appLintJar)
            }
        }
        return customRuleJars
    }



    /**
     * 得到gradle plugin版本号
     * */
    static String getGradleVersion(Project project){
        DependencySet dependencySet = project.getRootProject().buildscript.getConfigurations().getByName("classpath").dependencies
        def version
        for(Dependency dependency : dependencySet){
            if(dependency.group.equals("com.android.tools.build") && dependency.name.equals("gradle")){
                version = dependency.version
                break
            }
        }
        if(version == null){
            println("gradle plugin not exist!")
        }
        println("gradle version:"+version)
        return version
    }



    /**
     * 导出获得android gradle plugin插件的版本号，build.gradle中apply后可直接使用getAndroidGradlePluginVersionCompat()
     */
    @SuppressWarnings("GrMethodMayBeStatic")
    String getAndroidGradlePluginVersionCompat() {
        String version = null
        try {
            Class versionModel = Class.forName("com.android.builder.model.Version")
            def versionFiled = versionModel.getDeclaredField("ANDROID_GRADLE_PLUGIN_VERSION")
            versionFiled.setAccessible(true)
            version = versionFiled.get(null)
        } catch (Exception e) {
            version = "unknown"
        }
        return version
    }

    /**
     * 通过Git命令获取需要检查的文件列表
     * @param project
     * @return 文件名
     */
    static List<String> getCommitChange(Project project) {
        try {
            String projectDir = project.getRootDir().getAbsolutePath()
            String command = "git diff --name-only --diff-filter=ACMRTUXB HEAD~0 $projectDir"
            String changeInfo = command.execute(null, project.getRootDir()).text.trim()
            if (changeInfo == null || changeInfo.isEmpty()) {
                return new ArrayList<String>()
            }

            String[] lines = changeInfo.split("\\n")
            return lines.toList()
        } catch (Exception e) {
            e.printStackTrace()
            return new ArrayList<String>()
        }
    }
}
