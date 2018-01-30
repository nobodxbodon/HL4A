package hl4a.ide;

import 间.安卓.插件.辅助插件;
import 间.安卓.插件.附加插件;
import 间.安卓.组件.基本应用;

public class Application extends 基本应用 {

    @Override
    public void 应用创建事件() {
        new 辅助插件().注册(this);
        new 附加插件().注册(this);
    }
    
}
