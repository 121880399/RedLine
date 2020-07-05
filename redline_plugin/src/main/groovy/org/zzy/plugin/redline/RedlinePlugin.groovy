package org.zzy.plugin.redline

import com.android.tools.lint.checks.BuiltinIssueRegistry;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/7/3 16:19
 * 描    述：
 * 修订历史：
 * ================================================
 */
class RedlinePlugin implements Plugin<Project> {

    //低于1.8.5的Git版本使用--diff-filter=ACMRTUXB，高于可以用--diff-filter=d 来排除已经删除的文件
    //HEAD~1是前一个commit，HEAD~0是当前的commit
    private static final String GIT_COMMAND = "git diff --name-only --diff-filter=ACMRTUXB HEAD~1 HEAD~0"

    @Override
    void apply(Project project) {
        //<<跟doLast功能一样
        project.task("IncrementLint") << {
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
            def flag = lintGitClient.flags
            flag.setExitCode = true

            //2.开始lint检查,BuiltinIssueRegistry 系统定义的问题集
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
     * 通过Git命令获取需要检查的文件列表
     * @param project
     * @return 文件名
     */
    static List<String> getCommitChange(Project project) {
        try {
            String changeInfo = GIT_COMMAND.execute(null, project.getRootDir().text.trim())
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
