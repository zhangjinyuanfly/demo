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