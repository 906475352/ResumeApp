package com.example.xgguo1.recruitment;

import android.app.Application;

import cn.bmob.v3.Bmob;

/**
 * SDK初始化也可以放到Application中
 * 需要在mainfests中引入这个类文件
 */

public class BmobApplication extends Application{

    public static String APPID ="5973007dab74e4be576c38f2077adf2d";

    public void onCreate(){
        super.onCreate();

        //默认初始化
        Bmob.initialize(this,APPID);
    }
}

/**
 * 这里总结一下使用bmob的步骤：
 * 一：导入sdk,本地或在线（在buid.gradle改）
 * 二：自定义类继承Application初始化Bmob , 并在mainfests中声明该类
 * 三：在mainfests中设置bmob用到的权限
 * 四：新建Javabean继承bmobobject，使之与数据库表名字段对应
 *
 */