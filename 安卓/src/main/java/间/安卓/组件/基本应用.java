package 间.安卓.组件;

import android.app.*;
import android.content.*;
import android.content.res.*;
import hl4a.runtime.*;
import 间.安卓.工具.*;
import 间.工具.*;

public class 基本应用 extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        应用.初始化应用(this);
        应用创建事件();
    }

    @Override
    public void attachBaseContext(Context $上下文) {
        super.attachBaseContext($上下文);
        处理环境事件($上下文);
    }

    @Override
    public void onConfigurationChanged(Configuration $新设置) {
        super.onConfigurationChanged($新设置);
        设置改变事件($新设置);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        应用销毁事件();
    }


    public void 应用创建事件() {}
    public void 应用销毁事件() {}
    public void 处理环境事件(Context $上下文) {}
    public void 设置改变事件(Configuration $新设置) {}

}
