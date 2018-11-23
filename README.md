# umeng
友盟分享快速集成库

~~本库基于友盟6.4.4的分享模块版本开发. 请查看um644分支~~

更新友盟6.9.4

暂时只集成了, QQ和微信 精简版 的分享和登录功能.

[友盟接入文档](https://developer.umeng.com/docs/66632/detail/66639)

# 使用方法
## 1:
打开项目中的 `build.gradle` 文件:
你会看到配置以下信息:
```
UMENG_APPKEY: "替换你申请的友盟KEY",
PROJECT_NAME: "替换成开发中APP的包名(如 com.angcyo.demo)",
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

# 附录
### QQ 授权返回字段
|字段|值|
|:-|:-|
|unionid||
|access_token|48ED6CF2C42EB83A15E7152FEF86B507|
|page_type|
|appid|
|pfkey|6dcd5ed1a0c99fb4e3b615474a5ab8a5|
|uid|25669D12122479868B3C301B82AF27BE|
|auth_time|
|sendinstall|
|pf|desktop_m_qq-10000144-android-2002-|
|expires_in|7776000|
|pay_token|E04E9BA9FAC2A73764BF4271F4E2EEC4|
|ret|0|
|openid|25669D12122479868B3C301B82AF27BE|

### 微信授权返回字段
|字段|值|
|:-|:-|
|unionid|oD71AvwjJwgpDLN0xklYj8ZHT5gw|
|scope|snsapi_userinfo|
|expires_in|7200|
|access_token|0JtyoWx794WtPy36nrSRTpi84jWG2LpyeS0HGgIN1f55h1ztUlvI3xYpHdJKmpYXSGpRovztWO__EGzFOZqim40sTaFm982RxavMpFAQRBw|
|openid|oVtG3ww7PQf-KerxlEIbss3yL-XY|
|refresh_token|t-R6hhdizs3tojShYjGOw4ziQ803lMM0IpvMYqMxjzcW4OJkbJwmvom-jVXbW2ygaPurGogkSyfBcXgJYteQq7nee2UHyvNxS02WUw0dlHU|

### QQ 平台信息返回
|字段|值|
|:-|:-|
|unionid||
|is_yellow_vip|0|
|screen_name|　|
|msg||
|vip|0|
|city|南昌|
|accessToken|48ED6CF2C42EB83A15E7152FEF86B507|
|gender|男|
|province|江西|
|is_yellow_year_vip|0|
|openid|25669D12122479868B3C301B82AF27BE|
|yellow_vip_level|0|
|profile_image_url|http://q.qlogo.cn/qqapp/1106097075/25669D12122479868B3C301B82AF27BE/100|
|access_token|48ED6CF2C42EB83A15E7152FEF86B507|
|iconurl|http://q.qlogo.cn/qqapp/1106097075/25669D12122479868B3C301B82AF27BE/100|
|name|　|
|uid|25669D12122479868B3C301B82AF27BE|
|expiration|1502873607527|
|expires_in|1502873607527|
|level|0|
|ret|0|


### 微信 平台信息返回
|字段|值|
|:-|:-|
|unionid|oD71AvwjJwgpDLN0xklYj8ZHT5gw|
|screen_name|Spot|
|city|东莞|
|accessToken|0JtyoWx794WtPy36nrSRTpi84jWG2LpyeS0HGgIN1f55h1ztUlvI3xYpHdJKmpYXSGpRovztWO__EGzFOZqim40sTaFm982RxavMpFAQRBw|
|refreshToken|t-R6hhdizs3tojShYjGOw4ziQ803lMM0IpvMYqMxjzcW4OJkbJwmvom-jVXbW2ygaPurGogkSyfBcXgJYteQq7nee2UHyvNxS02WUw0dlHU|
|gender|0|
|province|广东|
|openid|oVtG3ww7PQf-KerxlEIbss3yL-XY|
|profile_image_url|http://wx.qlogo.cn/mmopen/CkBYF6IYNs07Vy1ibWfiajd8eZWLNdhicuzFyHRNOBFvb2LbzEtib4bt2oIbLrLlmgdXVCaHJzwNHia8E2wPgppnFv1AdamCw6yicB/0|
|country|中国|
|access_token|0JtyoWx794WtPy36nrSRTpi84jWG2LpyeS0HGgIN1f55h1ztUlvI3xYpHdJKmpYXSGpRovztWO__EGzFOZqim40sTaFm982RxavMpFAQRBw|
|iconurl|http://wx.qlogo.cn/mmopen/CkBYF6IYNs07Vy1ibWfiajd8eZWLNdhicuzFyHRNOBFvb2LbzEtib4bt2oIbLrLlmgdXVCaHJzwNHia8E2wPgppnFv1AdamCw6yicB/0|
|name|Spot|
|uid|oD71AvwjJwgpDLN0xklYj8ZHT5gw|
|expiration|1495103342757|
|language|zh_CN|
|expires_in|1495103342757|


### 联系作者
> 请使用QQ扫码加群, 小伙伴们在等着你哦!

![](https://raw.githubusercontent.com/angcyo/res/master/image/qq/qq_group_code.png)
