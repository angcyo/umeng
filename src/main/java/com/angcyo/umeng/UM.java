package com.angcyo.umeng;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.UmengTool;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/05/18 10:00
 * 修改人员：Robi
 * 修改时间：2017/05/18 10:00
 * 修改备注：
 * Version: 1.0.0
 */
public class UM {

    private static Application sApplication;

    /**
     * 请记得调用初始化方法
     */
    public static void init(Application application, boolean debug) {
        sApplication = application;

        Config.DEBUG = debug;

        getUMShareAPI();

        initPlatformConfig();

        MobclickAgent.setCatchUncaughtExceptions(true);
    }

    static void initPlatformConfig() {
        try {
            ApplicationInfo applicationInfo = sApplication.getPackageManager()
                    .getApplicationInfo(sApplication.getPackageName(), PackageManager.GET_META_DATA);

            String qq_id = String.valueOf(applicationInfo.metaData.get("QQ_ID"));
            String qq_key = String.valueOf(applicationInfo.metaData.get("QQ_KEY"));
            String wx_id = String.valueOf(applicationInfo.metaData.get("WX_ID"));
            String wx_key = String.valueOf(applicationInfo.metaData.get("WX_KEY"));

            PlatformConfig.setWeixin(wx_id, wx_key);
            PlatformConfig.setQQZone(qq_id, qq_key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除授权
     */
    public static void deleteAuth(Activity activity, SHARE_MEDIA platform, UMAuthListener listener) {
        getUMShareAPI().deleteOauth(activity, platform, listener);
    }

    /**
     * 授权验证
     */
    public static void authVerify(Activity activity, SHARE_MEDIA platform, UMAuthListener listener) {
        getUMShareAPI().doOauthVerify(activity, platform, listener);
    }

    /**
     * 返回是否授权
     */
    public static boolean isAuthorize(Activity activity, SHARE_MEDIA platform) {
        return getUMShareAPI().isAuthorize(activity, platform);
    }

    /**
     * 获取平台信息
     * <p/>
     * 如果未授权, 会拉取授权界面, 再返回数据
     * <p/>
     * 如果已授权, 会直接返回数据
     */
    public static void getPlatformInfo(Activity activity, SHARE_MEDIA platform, UMAuthListener listener) {
        getUMShareAPI().getPlatformInfo(activity, platform, listener);
    }

    /**
     * 分享纯文本
     * <p/>
     * <b>注意:QQ 不能分享纯文本</b>
     */
    public static void shareText(Activity activity, SHARE_MEDIA shareMedia,
                                 String shareText, UMShareListener listener) {
        new ShareAction(activity)
                .setPlatform(shareMedia)
                .withText(shareText)
                .setCallback(listener)
                .share();
    }

    /**
     * 分享图片
     * <p/
     * 分享到微信必须要设置缩略图
     */
    public static void shareImage(Activity activity, SHARE_MEDIA shareMedia,
                                  String imageUrl, int thumbRes,
                                  UMShareListener listener) {
        UMImage umImage = new UMImage(activity, imageUrl);
        if (thumbRes != -1) {
            UMImage umThumb = new UMImage(activity, thumbRes);
            umImage.setThumb(umThumb);
        }
        new ShareAction(activity)
                .setPlatform(shareMedia)
                .withMedia(umImage)
                .setCallback(listener)
                .share();
    }

    /**
     * 分享图文
     */
    public static void shareImageText(Activity activity, SHARE_MEDIA shareMedia,
                                      String imageUrl, int thumbRes,
                                      String text,
                                      UMShareListener listener) {
        UMImage umImage = new UMImage(activity, imageUrl);
        if (thumbRes != -1) {
            UMImage umThumb = new UMImage(activity, thumbRes);
            umImage.setThumb(umThumb);
        }
        new ShareAction(activity)
                .setPlatform(shareMedia)
                .withMedia(umImage)
                .withText(text)
                .setCallback(listener)
                .share();
    }

    /**
     * 分享链接
     */
    public static void shareWeb(Activity activity, SHARE_MEDIA shareMedia,
                                String url, int thumbRes,
                                String title, String des,
                                String text,
                                UMShareListener listener) {
        shareWeb(activity, shareMedia, url,
                thumbRes == -1 ? null : new UMImage(activity, thumbRes),
                title, des, text, listener);
    }

    public static void shareWeb(Activity activity, SHARE_MEDIA shareMedia,
                                String url, String thumbRes,
                                String title, String des,
                                String text,
                                UMShareListener listener) {
        shareWeb(activity, shareMedia, url,
                TextUtils.isEmpty(thumbRes) ? null : new UMImage(activity, thumbRes),
                title, des, text, listener);
    }

    public static void shareWeb(Activity activity, SHARE_MEDIA shareMedia,
                                String url, UMImage thumbImage,
                                String title, String des,
                                String text,
                                UMShareListener listener) {
        UMWeb web = new UMWeb(url);
        web.setTitle(title);//标题
        //缩略图
        if (thumbImage != null) {
            web.setThumb(thumbImage);
        }
        web.setDescription(des);//描述

        new ShareAction(activity)
                .setPlatform(shareMedia)
                .withMedia(web)
                .withText(text)
                .setCallback(listener)
                .share();
    }

    public static void checkQQ(Activity activity) {
        UmengTool.checkQQ(activity);
    }

    public static void checkWX(Activity activity) {
        UmengTool.checkWx(activity);
    }

    public static void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (getUMShareAPI() != null) {
            getUMShareAPI().onActivityResult(requestCode, resultCode, data);
        }
    }

    private static UMShareAPI getUMShareAPI() {
        UMShareAPI umShareAPI = null;
        if (sApplication != null) {
            umShareAPI = UMShareAPI.get(sApplication);
        }
        return umShareAPI;
    }

    public static void onDestroy() {
        getUMShareAPI().release();
    }

    /**
     * 友盟事件统计
     */
    public static void onEvent(String eventId) {
        MobclickAgent.onEvent(sApplication, eventId);
    }

    public static abstract class UMListener implements UMShareListener {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {

        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            throwable.printStackTrace();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {

        }
    }
}
