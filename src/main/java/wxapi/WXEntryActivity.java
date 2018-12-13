package wxapi;


/**
 * 使用微信分享或者登陆功能
 * <p>
 * 在包名目录下创建wxapi文件夹，
 * 新建一个名为WXEntryActivity的activity继承WXCallbackActivity。
 * 这里注意一定是包名路径下，例如我的包名是com.umeng.soexample,则配置如下：
 *
 * 路径是com.umeng.socialize.weixin.view.WXCallbackActivity)
 */
//import com.umeng.weixin.callback.WXCallbackActivity;//精简版导入这个

//完整版导入这个

import com.umeng.socialize.weixin.view.WXCallbackActivity;

//com.umeng.socialize.weixin.view.WXCallbackActivity

/**
 * 请将此文件复制到 开发app的包名.wxapi 文件夹中.
 */
public class WXEntryActivity extends WXCallbackActivity {

}
