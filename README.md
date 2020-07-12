# 红线
<h1 align="center">
  <img src="https://raw.githubusercontent.com/121880399/PictureManager/master/%E7%BA%A2%E7%BA%BF.png" height="340" alt="logo" />
 </h1>

![](https://img.shields.io/badge/language-java-orange.svg)
### 背景

​	在团队开发中，我们如何保证每个成员都按照公司的代码规范进行编码？如何去控制线程数量？如何确保对线程命名？你是否发现很多的线上bug都是因为在开发过程中疏忽导致的？能不能在上线之前检测到这些问题并解决掉？Lint静态扫描就是一种好的解决方案。

### 介绍

​	红线是一个自定义Lint库，集成了常用的编码规则，可以与git工具结合实现增量扫描。

### 功能与特点

​	结合Git实现提交前增量检测，检测不通过commit不成功。

#### 	检测范围：

1. 检测Activity中的布局是否以activity_开头。
2. 检测Activity和Fragment是否继承于BaseActivity和BaseFragment。
3. 检测代码中出现中文的地方。
4. 检测常量是否使用大写。
5. 检测是否使用了枚举。
6. 检测for循环的深度。
7. 检测是否直接使用了系统的Log API。
8. 检测是否直接使用了new Message。
9. 检测parse类型的方法是否添加了try catch语句。
10. 检测是否直接调用了Throwable.printStackTrace语句。
11. 检测实现了Serializeble类中的内部类是否也同样实现了Serializedble。
12. 检测是否直接使用了new Thread创建线程。
13. 检测代码中是否存在Todo尚未完成。（暂时没法使用）
14. 检测Xml布局中，view的id命名是否规范。
15. 检测动态广播是否被注销。（暂时没法使用）

### 快速接入

当前版本是alpha版本，请不要使用到商业应用中，但是其中代码可以当做参考。

推荐将项目clone到本地以后，将redline_arr,redline_jar,redline_plugin导入到项目中。

如果存在多APP使用的情况，可以将其打包上传到本地Maven库。

如果只使用全量检测，那么可以在redline_jar中定制完你的规则以后，直接使用gradle命令进行。

```
gradlew lintDebug
```

或者指定在编译前检测，但是这会有两个问题，如果代码较多，编译时间会很长。因为是先检测再编译，所以对于字节码的检查，可能会有问题。

```groovy
android.applicationVariants.all { variant ->
    variant.outputs.each { output ->
        def lintTask = tasks["lint${variant.name.capitalize()}"]
        output.assemble.dependsOn lintTask
    }
}
```

如果是结合git实现增量检测，那么需要先修改跟目录下面的两个文件：

1.pre-commit

2.pre-commit-windows

其中pre-commit是给非windows使用的，因为团队中会有人使用不同的系统，所以这两个脚本文件都需要进行修改，当前里面使用的是shell命令，你还可以使用groovy等其他命令。文件内容主要是调用increatmentLint 任务，执行增量扫描，如果有错误会抛出异常来中断commit。

编写完这两个脚本以后，可以找到copyToGitHooks任务，直接点击或者执行gradlew copyToGitHook命令也可以。会将脚本文件复制到项目.git/hooks/文件夹下面。

然后将需要检查的module添加插件:

```groovy
apply plugin:'redline'

redLineConfig{
    excludeDirs = ["redline_jar/src/main/java/org/zzy/lib/redline/detector",
                   "redline_plugin/src/main/groovy/org/zzy/plugin/redline"]
    excludeFiles = ["lint-check-result.html"]
}
```

也可以添加排除的文件和文件夹。由于增量扫描依赖于git，所以增量文件是通过git diff得到的增量文件，可能是整个项目的，所以没不要对所有的module都添加插件。

最后正常的使用git commit提交代码就行，会自动执行lint扫描，如果存在问题本次commit会中断。如果使用android studio的工具进行提交，记得勾选上Run Git hooks。

### 存在问题

1.增量扫描时，输出的lint html报告中文有乱码问题。

2.增量扫描时，只导入了android.jar，目前还是写死的29版本。

3.redline_arr如果打包成aar上传到本地maven库，只能进行全量扫描，不能进行增量扫描，因为目前获取自定义Lint规则的方式是通过遍历目录获取jar包。

4.增量扫描导入的包不全，可能存在进行增量扫描的时候，某些属性返回Null的情况，而这种情况在全量扫描时不存在。

### ToDoList

1.解决增量扫描报告中文乱码问题。

2.增量扫描时将项目依赖的所有包都导入。

3.优化Lint自定义规则jar包的获取方式。

4.实现动态配置，减少修改自定义规则。

5.增加增量扫描方式，目前是跟上一次commit进行比较，增加对指定commit进行比较。

### Change Log

1.alpha 1.0 (2020/7/12) ：

	- 集成自定义Lint规则 
	- 实现初步的增量扫描

### 相关阅读

[1.美团外卖Android Lint代码检查实践](https://tech.meituan.com/2018/04/13/waimai-android-lint.html)

[2.Android自定义Lint实践](https://tech.meituan.com/2016/03/21/android-custom-lint.html)

### License

Apache 2.0