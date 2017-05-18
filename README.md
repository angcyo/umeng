# umeng
友盟分享快速继承库

本库基于友盟6.4.4的分享模块版本开发.

暂时只集成了, QQ和微信 的分享和登录功能.

# 使用方法
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
