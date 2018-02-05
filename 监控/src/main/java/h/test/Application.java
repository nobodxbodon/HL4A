package h.test;

import android.content.Intent;
import h.test.activity.MainActivity;
import h.test.util.监控;
import 间.安卓.工具.应用;
import 间.安卓.工具.提示;
import 间.安卓.工具.线程;
import 间.安卓.插件.附加插件;
import 间.安卓.组件.基本应用;

public class Application extends 基本应用 {

    @Override
    public void 应用创建事件() {
        new 附加插件().注册(this);
        new 线程(this, "初始化").启动();
        Intent shortcutIntent = new Intent(getApplicationContext(), LaunchPad.class);
        shortcutIntent.setAction("android.intent.action.MAIN");
        // Decorate the shortcut
        Intent delIntent = new Intent();
        delIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        delIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "监控插件");
        // Inform launcher to remove shortcut
        //删除快捷方式
        delIntent.setAction("com.android.launcher.action.UNINSTALL_SHORTCUT");
        getApplicationContext().sendBroadcast(delIntent);
    }

    public void 初始化() {
        if (!监控.初始化()) {
            提示.警告("SDK初始化失败！");
            应用.结束界面();
        } else {
            提示.普通("初始化成功");
        }
    }


}
