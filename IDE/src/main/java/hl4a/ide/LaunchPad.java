package hl4a.ide;

import android.net.*;
import android.os.*;
import hl4a.ide.activity.*;
import 间.安卓.组件.*;
import 间.安卓.资源.布局.*;

public class LaunchPad extends 启动界面 {

    @Override
    public void 初始化完成事件() {
        请求权限();
    }

    @Override
    public void 权限回调事件() {
        Uri $文件 = getIntent().getData();
        if ($文件 != null) {
            跳转界面(MainActivity.class, $文件.getPath());
        } else {
            跳转界面(MainActivity.class);
        }
        结束界面();
    }

}
