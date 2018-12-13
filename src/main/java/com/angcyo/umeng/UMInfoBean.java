package com.angcyo.umeng;

/**
 * Email:angcyo@126.com
 *
 * @author angcyo
 * @date 2018/12/13
 */
public class UMInfoBean {

   /* UShare封装后字段名
            QQ原始字段名
    微信原始字段名
            新浪原始字段名
    字段含义
            备注
    uid
            openid
    unionid
            id
    用户唯一标识
    uid能否实现Android与iOS平台打通，目前QQ只能实现同APPID下用户ID匹配
            name
    screen_name
            screen_name
    screen_name
            用户昵称
    gender
            gender
    gender
            gender
    用户性别
            该字段会直接返回男女
    iconurl
            profile_image_url
    profile_image_url
            profile_image_url
    用户头像*/

    /**
     * 完整信息查看
     * https://developer.umeng.com/docs/66632/detail/66639#h2-u6388u6743u57FAu672Cu4FE1u606Fu89E3u6790u5982u4E0B15
     */


    private String uid = "";
    private String name = "";
    /**
     *  //未验证 "0":男 "1":女, QQ 微信不返回 用户性别, 始终为 "0"
     */
    private String gender = "0";
    private String iconurl = "";

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIconurl() {
        return iconurl;
    }

    public void setIconurl(String iconurl) {
        this.iconurl = iconurl;
    }
}
