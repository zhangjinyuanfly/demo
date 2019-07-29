[Lint](https://developer.android.com/studio/write/lint?hl=zh-CN)

执行：gradlew lint

如果您只想为特定构建变体运行 `lint` 任务，您必须大写变体名称并在其前面加上 `lint` 前缀

`lintDebug`  `lintRelease` `lintSmDisableLoginForceFullScreenDisableCtaDisablePreviewNRelease`



系统预制lint：C:\Users\zhangjinyuan\.gradle\caches\modules-2\files-2.1\com.android.tools.lint\lint-checks\24.5.0\dbdca0447c2333481823e088a356393e653276b4\lint-checks-24.5.0.jar



忽略lint检查

@SuppressLint("all")

lint.xml配置

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



自定义lint：

版本：

Android Studio 3.X 

lint:26

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

**ResourceFolderScanner** 
**UastScanner**
**XmlScanner**
**FileScanner**
BinaryResourceScanner
ClassScanner 
GradleScanner 
OtherFileScanner



 





全局使用：

生成的lint.jar 放在\.android\lint\  命令启动lint检查

引入工程中：作为module引入，使用lintChecks

```groovy
lintChecks project(':lintlib')
```





git hooks  commit 检查：创建  pre-commit 提交前检查

```shell
#!D:/soft/Git/bin/sh.exe
work_path=$(dirname $0)
cd d:/github/demo/demo/customlint
./gradlew lintDebug
exit 1 # 1中断，0继续执行
```



lint 调试开发

环境变量
GRADLE_OPTS="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"

gradlew.bat clean lintDebug -Dorg.gradle.daemon=false -Dorg.gradle.debug=true --no-daemon

