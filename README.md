# umeng
友盟分享快速继承库

本库基于友盟6.4.4的分享模块版本开发.

暂时只集成了, QQ和微信 的分享和登录功能.

# 使用方法
## 1:
打开项目中的 `build.gradle` 文件:
你会看到配置以下信息:
```
/*友盟APP_KEY*/
UMENG_APPKEY: "替换你申请的友盟KEY",
/*需要使用库的工程包名*/
PROJECT_NAME: "替换成开发中APP的包名(如 com.angcyo.demo)",
/*开放平台申请key*/
QQ_ID       : "QQ开放平台的APPID",
QQ_KEY      : "QQ开放平台的APPKEY",
WX_ID       : "微信开放平台的AppID",
WX_KEY      : "微信开放平台的AppSecret"
```

根据提示填写即可;

## 2:
你需要创建一个 `开发中APP的包名路径.wxapi` 这样的文件夹, 并且把 `WXEntryActivity.java` 文件, 复制到该文件夹下即可.

## 3:
调用 `UM.init();` 进行初始化操作, 之后即可使用友盟分享组件.

**请注意,在您调用的Activity中onActivityResult方法中 需要调用UM.onActivityResult()方法进行处理, 否则回调可能不会执行.**

# 提供了一些简单的方法如下
|方法名|作用|
|:---|:---|
|init()|初始化, 必须调用|
|deleteAuth()|删除授权|
|authVerify()|开始授权|
|isAuthorize()|判断是否授权|
|getPlatformInfo()|获取平台用户信息, 如果未授权,会拉取授权界面, 否则直接返回信息|
|shareText()|分享纯文本(QQ不支持)|
|shareImage()|分享图片|
|shareImageText()|分享文本和图片|
|shareWeb()|分享链接|

> 您也可以自定义缺失的方法.

