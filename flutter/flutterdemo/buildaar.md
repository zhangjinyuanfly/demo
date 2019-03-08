## 编译AAR
因为aar引入主项目中，manifest中的属性有可能可主项目冲突，需要更改和删掉一些配置信息：

### 1. build.gradle 修改，路径：android/app/build.gradle
```gradle

apply plugin: 'com.android.application' 调整为 apply plugin: 'com.android.library'

修改：minSdkVersion targetSdkVersion compileSdkVersion 和主项目保持一致

去掉 applicationId "com.example.flutterdemo"

```
### 2. AnidroidManifest.xml 修改，路径：android/app/src/main/AnidroidManifest.xml
```xml
去掉启动相关 Activity配置
去掉<application>标签中和主工程冲突的属性：android:name android:label android:icon

```
### 3. flutter1.2版本已经不需要这个步骤了。// 执行 flutter build apk 默认release包，生成的aar路径：build/app/outputs/aar，这个aar会缺少flutter_shared/icudt.dat文件，可以把apk包中的这个文件copy到arr的对应位置来。

### 4. build成功的aar引入主工程：

##### 1）第一种引入方式
```java
public class MyActivity extends FlutterActivity {
 
    public void onCreate(@Nullable Bundle savedInstanceState) {
        
        FlutterMain.startInitialization(this);// flutter初始化，加载flutter前一定要调用
        super.onCreate(savedInstanceState);
        GeneratedPluginRegistrant.registerWith(this);
    }
}
```
##### 2）第二种引入方式，第二种需要把flutter module方式中的部分类引入进来 <code>io.flutter.facade.Flutter</code>和<code>io.flutter.facade.FlutterFragment</code>，注意不要引入GeneratedPluginRegistrant类，会和aar中的冲突

```java
public class TwoActivity extends AppCompatActivity {
 
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FlutterView flutterView = Flutter.createView(this, getLifecycle(), "dart参数用于获取不同的widget");
        setContentView(flutterView);
    }
}
```