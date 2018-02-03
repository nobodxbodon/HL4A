package h.test;

import android.net.Uri;
import android.os.Bundle;
import h.test.activity.MainActivity;
import 间.安卓.组件.基本界面;
import 间.安卓.资源.布局.布局_初始化;

public class LaunchPad extends 基本界面 {

    @Override
    public void 界面创建事件(Bundle $恢复) {
        打开布局(new 布局_初始化(this));
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
