package hl4a.ide;

import 间.安卓.组件.*;
import 间.安卓.插件.附加;

public class Application extends 基本应用 {

    @Override
    public void 应用创建事件() {
        new 附加().注册(this);
    }
    
}
