
解析快手短视频分享的链接，获取到播放的MP4链接，最后在浏览器中打开MP4链接进行下载

App页面截图:


![](./screenshot/Screenshot_2018-06-10-22-05-58.png)

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

尝试使用 jadx 反编译 快手app,发现请求中的签名字段已改为 `sig2`
