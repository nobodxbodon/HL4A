package 间.安卓.插件;

import 间.安卓.组件.基本界面;
import 间.安卓.工具.权限;

public class 附加插件 extends 应用插件 {

    @Override
    public void 界面新建(基本界面 $界面) {
        $界面.注册插件(new 界面附加());
    }
    
    public static class 界面附加 extends 界面插件 {

        @Override
        public void 请求权限事件() {
            权限.默认请求(界面);
        }
        
    }

}
