package 间.安卓.实例;

import android.os.Bundle;
import java.io.File;
import 间.安卓.工具.应用;
import 间.安卓.工具.文件;
import 间.安卓.工具.线程;
import 间.安卓.工具.设置;
import 间.安卓.组件.启动界面;
import 间.安卓.资源.布局.布局_初始化;
import 间.工具.ZIP;
import 间.工具.字节;
import 间.工具.编码;
import 间.工具.字符;

public class 实例启动 extends 启动界面 {

    @Override
    public void 初始化完成事件() {
        文件.替换地址("./",文件.检查地址("$脚本") + "/");
        new 线程(this,"初始化脚本").启动();
    }

    @Override
    public void 权限回调事件() {
        跳转脚本("./入口.js");
    }
    
    public void 初始化脚本() {
        if (文件.自身变更("实例变更")) {
            ZIP.解压("#classes1.dex","./");
        }
        请求权限();
    }

}
