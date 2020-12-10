
解析快手短视频分享的链接，获取到播放的MP4链接，最后在浏览器中打开MP4链接进行下载

App页面截图:


![](./screenshot/Screenshot_2018-06-10-22-05-58.png)

### Todo List

- [ ] 了解Rxjava2+Retrofit2+MVP的网络请求封装
- [ ] 添加一个配置读取配置的输入框，默认是使用本地的配置JSON文件，也可以使用网络上的配置
- [ ] 网络的配置文件，需要验证格式是正确

Give up :cry:

##20201210
[[Android基础] VideoView](https://www.jianshu.com/p/2d3b221a2ee7)
[Android VideoView比例缩放](http://www.voidcn.com/article/p-vuxjkawj-btx.html)
[Android开发 VideoView视频播放详解](https://www.cnblogs.com/guanxinjing/p/11177739.html) 对布局的说明比较详细，虽然没有解决问题

固定解析的Url播放视频的尺寸，测试的设备: 荣耀Play

#### 20180622

1. 更新了截取规则，了解到 java解析多级JSON规则，需要一级一级的解析下去，参考[json-simple 解析多级json字符串](https://blog.csdn.net/qq_21682469/article/details/78953896)

如:

json 内容

```
{
  "work":{
     "currentWork":{
        "playUrl":"https://www.example.com"
     }
  }
}
```

获取上列json字符串中的 `playUrl` 代码如:

```
JSONObject jsonObject =  new JSONObject(responseResult); //json 字符串
JSONObject work = (JSONObject) jsonObject.get("work");
JSONObject currentWork = (JSONObject) work.get("currentWork");
String playUrl = currentWork.optString("playUrl");
```

#### 2019-05-09

尝试使用 jadx 反编译 快手app(6.4.0.9003),发现请求中的签名字段已改为 `sig2`

#### 2019-05-23

Android Studio ide的版本 更新到 3.1.4

1. org.gradle.api.internal.tasks.compile.CompilationFailedException: Compilation failed; see the compil
2. org.gradle.api.tasks.TaskExecutionException: Execution failed for task ':app:compileDebugJavaWithJava
3. com.android.builder.dexing.DexArchiveBuilderException: Failed to process
4. ` Static interface methods are only supported starting with Android N`


程序包com.google.common.collect不存在

在 app的 build.gradle文件中，添加 [google/guava](https://github.com/google/guava)

```
api 'com.google.guava:guava:27.1-android'
```

---

1. `http://www.gifshow.com/fw/photo/5229242126404827955?userId=3xwccfweqip6x9i&photoId=3x8n2rkgyp39q7k&cc=share_copylink&timestamp=1559144677719&et=1_i%2F1634881657273335808_h86`

这个链接需要登录的web用户才能转发到




# Reference

1. [成功解决org.gradle.api.internal.tasks.compile.CompilationFailedException: Compilation failed;](https://blog.csdn.net/mp624183768/article/details/81118249)
2. [快速解决 org.gradle.api.tasks.TaskExecutionException: Execution failed for task ':app:compileJava'](https://www.jianshu.com/p/e83b2aa15965)
3. [Android studio升级后原有项目无法正常编译运行问题](https://www.cnblogs.com/walker-world/p/9772564.html)
4. [项目运行报错Error: Static interface methods are only supported starting with Android N (--min-api 24)](https://www.jianshu.com/p/49ff4c7c1e29)