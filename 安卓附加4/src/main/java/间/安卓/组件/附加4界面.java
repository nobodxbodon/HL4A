package 间.安卓.组件;

import 间.安卓.工具.*;

public class 附加4界面 extends 基本界面  {

    @Override
    public void 请求权限() {
        if (设备.取SDK() < 23) {
            权限回调事件();
            return;
        }
        权限.默认请求(this);
    }

}
