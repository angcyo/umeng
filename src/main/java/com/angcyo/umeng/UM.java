package com.angcyo.umeng;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.*;
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
        UMConfigure.setLogEnabled(debug);

        UMShareAPI.get(sApplication);

        initPlatformConfig();

        try {
            ApplicationInfo applicationInfo = sApplication.getPackageManager()
                    .getApplicationInfo(sApplication.getPackageName(), PackageManager.GET_META_DATA);

            String umChannel = String.valueOf(applicationInfo.metaData.get("UMENG_CHANNEL"));
            String umKey = String.valueOf(applicationInfo.metaData.get("UMENG_APPKEY"));

            UMConfigure.init(application, umKey, umChannel, UMConfigure.DEVICE_TYPE_PHONE, "" /*推送服务secret*/);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
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
        UMShareAPI.get(sApplication).deleteOauth(activity, platform, listener);
    }

    /**
     * 授权验证
     */
    public static void authVerify(Activity activity, SHARE_MEDIA platform, UMAuthListener listener) {
        UMShareAPI.get(sApplication).doOauthVerify(activity, platform, listener);
    }

    /**
     * 返回是否授权
     */
    public static boolean isAuthorize(Activity activity, SHARE_MEDIA platform) {
        return UMShareAPI.get(sApplication).isAuthorize(activity, platform);
    }

    /**
     * 获取平台信息
     * <p/>
     * 如果未授权, 会拉取授权界面, 再返回数据
     * <p/>
     * 如果已授权, 会直接返回数据
     */
    public static void getPlatformInfo(Activity activity, SHARE_MEDIA platform, UMAuthListener listener) {
        UMShareAPI.get(sApplication).getPlatformInfo(activity, platform, listener);
    }

    /**
     * 分享纯文本
     * <p/>
     * <b>注意:QQ 不能分享纯文本</b>
     */
    public static void shareText(Activity activity, SHARE_MEDIA shareMedia,
                                 String shareText, UMShareListener listener) {
        if (shareMedia == SHARE_MEDIA.QQ) {
            //将文本转成图片
            FrameLayout frameLayout = new FrameLayout(activity);
            frameLayout.setBackgroundColor(Color.WHITE);

            TextView textView = new TextView(activity);
            textView.setText(shareText);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
            frameLayout.setPadding(4, 4, 4, 4);
            frameLayout.addView(textView, new ViewGroup.LayoutParams(-2, -2));

            DisplayMetrics metrics = activity.getResources().getDisplayMetrics();
            frameLayout.measure(View.MeasureSpec.makeMeasureSpec(metrics.widthPixels, View.MeasureSpec.AT_MOST),
                    View.MeasureSpec.makeMeasureSpec(metrics.heightPixels, View.MeasureSpec.AT_MOST));
            frameLayout.layout(0, 0, frameLayout.getMeasuredWidth(), frameLayout.getMeasuredHeight());

            Bitmap bitmap = Bitmap.createBitmap(frameLayout.getMeasuredWidth(), frameLayout.getMeasuredHeight(), Bitmap.Config.ARGB_4444);
            Canvas canvas = new Canvas(bitmap);
            frameLayout.draw(canvas);

            shareImage(activity, shareMedia, bitmap, -1, listener);
        } else {
            action(activity, shareMedia, listener)
                    .withText(shareText)
                    .share();
        }
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
        action(activity, shareMedia, listener)
                .withMedia(umImage)
                .share();
    }

    /**
     * 分享Bitmap对象
     */
    public static void shareImage(Activity activity, SHARE_MEDIA shareMedia,
                                  Bitmap bitmap, int thumbRes,
                                  UMShareListener listener) {
        UMImage umImage = new UMImage(activity, bitmap);
        if (thumbRes != -1) {
            UMImage umThumb = new UMImage(activity, thumbRes);
            umImage.setThumb(umThumb);
        }
        action(activity, shareMedia, listener)
                .withMedia(umImage)
                .share();
    }

    private static ShareAction action(Activity activity, SHARE_MEDIA shareMedia,
                                      UMShareListener listener) {
        return new ShareAction(activity)
                .setPlatform(shareMedia)
                .setCallback(listener);
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
        action(activity, shareMedia, listener)
                .withMedia(umImage)
                .withText(text)
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
        UMWeb web = new UMWeb(url);
        web.setTitle(title);//标题
        //缩略图
        if (thumbRes != -1) {
            UMImage umThumb = new UMImage(activity, thumbRes);
            web.setThumb(umThumb);
        }
        web.setDescription(des);//描述

        action(activity, shareMedia, listener)
                .withMedia(web)
                .withText(text)
                .share();
    }

    public static void checkQQ(Activity activity) {
        UmengTool.checkQQ(activity);
    }

    public static void checkWX(Activity activity) {
        UmengTool.checkWx(activity);
    }

    public static void onActivityResult(int requestCode, int resultCode, Intent data) {
        UMShareAPI.get(sApplication).onActivityResult(requestCode, resultCode, data);
    }

    public static void onDestroy() {
        UMShareAPI.get(sApplication).release();
    }
}
