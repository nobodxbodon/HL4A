package hnhnh.jk;

import android.net.Uri;
import android.os.Bundle;
import hnhnh.jk.activity.MainActivity;
import 间.安卓.组件.基本界面;
import 间.安卓.资源.布局.布局_初始化;
import hnhnh.jk.util.监控;

public class LaunchPad extends 基本界面 {

    @Override
    public void 界面创建事件(Bundle $恢复) {
        打开布局(new 布局_初始化(this));
        请求权限();
    }

    @Override
    public void 权限回调事件() {
        跳转界面(MainActivity.class);
        结束界面();
    }

    @Override
    public void 界面销毁事件() {
        监控.释放();
    }

}
