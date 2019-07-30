[Lint](https://developer.android.com/studio/write/lint?hl=zh-CN)

执行：手动执行，命令执行

gradlew lint

为特定构建变体运行 `lint` 任务，必须大写变体名称并在其前面加上 `lint` 前缀

`lintDebug`  `lintRelease` `lintSmDisableLoginForceFullScreenDisableCtaDisablePreviewNRelease`

## 基本使用

Gradle配置：

```groovy
lintOptions {
    //Lint检查文件的类型，默认是.java和.xml。可以自定义其他类型的文件
    lintCheckFileType = ".java,.xml" 
    // 重置 lint 配置（使用默认的严重性等设置）。
    lintConfig file("default-lint.xml")
    // 如果为 true，则当lint发现错误时停止 gradle构建
    abortOnError false
    // 如果为 true，则只报告错误
    ignoreWarnings true
    // 设置为 true时lint将不报告分析的进度
    quiet true
    //默认是false。为true的时候会扫描git commit时候所有的代码并且输出扫描
    lintReportAll = false 
    // 如果为 true，则当有错误时会显示文件的全路径或绝对路径 (默认情况下为true)
    //absolutePaths true
    // 如果为 true，则检查所有的问题，包括默认不检查问题
    checkAllWarnings true
    // 如果为 true，则将所有警告视为错误
    warningsAsErrors true
    // 不检查给定的问题id
    disable 'TypographyFractions','TypographyQuotes'
    // 检查给定的问题 id
    enable 'RtlHardcoded','RtlCompat', 'RtlEnabled'
    // * 仅 * 检查给定的问题 id
    check 'NewApi', 'InlinedApi'
    // 如果为true，则在错误报告的输出中不包括源代码行
    noLines true
    // 如果为 true，则对一个错误的问题显示它所在的所有地方，而不会截短列表，等等。
    showAll true
    // 如果为 true，生成一个问题的纯文本报告（默认为false）
    textReport true
    // 配置写入输出结果的位置；它可以是一个文件或 “stdout”（标准输出）
    textOutput 'stdout'
    // 如果为真，会生成一个XML报告，以给Jenkins之类的使用
    xmlReport false
    // 用于写入报告的文件（如果不指定，默认为lint-results.xml）
    xmlOutput file("lint-report.xml")
    // 如果为真，会生成一个HTML报告（包括问题的解释，存在此问题的源码，等等）
    htmlReport true
    // 写入报告的路径，它是可选的（默认为构建目录下的 lint-results.html ）
    htmlOutput file("lint-report.html")
    // 设置为 true， 将使所有release 构建都以issus的严重性级别为fatal（severity=false）的设置来运行lint
    // 并且，如果发现了致命（fatal）的问题，将会中止构建（由上面提到的 abortOnError 控制）
    checkReleaseBuilds true
    // 设置给定问题的严重级别（severity）为fatal （这意味着他们将会
    // 在release构建的期间检查 （即使 lint 要检查的问题没有包含在代码中)
    fatal 'NewApi', 'InlineApi'
    // 设置给定问题的严重级别为error
    error 'Wakelock', 'TextViewEdits'
    // 设置给定问题的严重级别为warning
    warning 'ResourceAsColor'
    // 设置给定问题的严重级别（severity）为ignore （和不检查这个问题一样）
    ignore 'TypographyQuotes'
    
}

```



忽略lint检查：

1. 代码忽略：`@SuppressLint("all")`

2. lint.xml配置：

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <lint>    
       <!-- Disable the given check in this project -->    
       <issue id="IconMissingDensityFolder" severity="ignore" />    
       <!-- Ignore the ObsoleteLayoutParam issue in the specified files -->    	<issue id="ObsoleteLayoutParam">        
           <ignore path="res/layout/activation.xml" />
           <ignore path="res/layout-xlarge/activation.xml" />
       </issue>    
       <!-- Ignore the UselessLeaf issue in the specified file -->    
       <issue id="UselessLeaf">
           <ignore path="res/layout/main.xml" />
       </issue>    
       <!-- Change the severity of hardcoded strings to "error" -->    
       <issue id="HardcodedText" severity="error" />
   </lint>
   ```

3. XML忽略：`tools:ignore="issueId"`



## 自定义lint：

系统lint路径：`.gradle\caches\modules-2\files-2.1\com.android.tools.lint\lint-checks\24.5.0\dbdca0447c2333481823e088a356393e653276b4\lint-checks-24.5.0.jar`

自定义lint

Android Studio 3.X 



`IssueRegistry`：入口，配置项

`Detector`：实现类

```groovy
apply plugin: 'java'
dependencies {
    compileOnly 'com.android.tools.lint:lint-api:26.2.0'
    compileOnly 'com.android.tools.lint:lint-checks:26.2.0'
}
jar {
    manifest {
        attributes("Lint-Registry-v2": "com.zjy.lintlib.CustomRegister")
    }
}
tasks.withType(JavaCompile) {
    options.encoding = "UTF-8"
}
```

Detector：支持的检查类型：

1. ISSUE：定义检查项

2. 实现扫描接口

   **UastScanner**
   **XmlScanner**
   ResourceFolderScanner
   FileScanner
   BinaryResourceScanner
   ClassScanner 
   GradleScanner 
   OtherFileScanner

3. report错误



### 1.全局使用：

生成的lint.jar 放在\.android\lint\  命令启动lint检查

\.android\lint\lint.jar

### 2.引入工程中：作为module引入，使用lintChecks

```groovy
lintChecks project(':lintlib')
```

检查时机：

1. coding过程代码提示：

2. 编译过程中检查，会降低编译速度

   ```groovy
   // compile过程检查lint
   android.applicationVariants.all { variant ->
       variant.outputs.each { output ->
           def lintTask = tasks["lint${variant.name.capitalize()}"]
           output.assemble.dependsOn lintTask
       }
   }
   ```

3. git hooks  commit 检查：创建  pre-commit 提交前检查

```shell
#!D:/soft/Git/bin/sh.exe
work_path=$(dirname $0)
cd d:/github/demo/demo/customlint
./gradlew lintDebug
exit 1 # 1中断，0继续执行
```



## lint 调试开发

环境变量
GRADLE_OPTS="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"

gradlew.bat clean lintDebug -Dorg.gradle.daemon=false -Dorg.gradle.debug=true --no-daemon

