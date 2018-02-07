package 间.安卓.实例;

import android.os.Bundle;
import java.io.File;
import 间.安卓.工具.应用;
import 间.安卓.工具.文件;
import 间.安卓.工具.线程;
import 间.安卓.工具.设置;
import 间.安卓.组件.基本界面;
import 间.安卓.资源.布局.布局_初始化;
import 间.工具.ZIP;
import 间.工具.字符;
import 间.工具.字节;
import 间.工具.编码;

public class 启动界面 extends 基本界面 {

    @Override
    public void 界面创建事件(Bundle $恢复) {
        文件.替换地址("./", 文件.检查地址("$脚本") + "/");
        打开布局(new 布局_初始化(this));
        请求权限();
    }

    public void 初始化() {
        String $文件 = "classes1.dex";
        String $安装包 = 应用.取安装包位置();
        Integer $上次 =  new Long(new File($安装包).lastModified()).intValue();
        Integer $记录 = (Integer)设置.读取("上次安装");
        if ($记录 == null || !$上次.equals($记录)) {
            文件.删除("./");
            String $缓存 = "$cache/js.zip";
            String $字符 = 字节.读取(getClassLoader().getResourceAsStream($文件));
            字节.保存($缓存, 编码.Base64.解码($字符));
            文件.删除("./");
            ZIP.解压($缓存, "./");
            文件.删除($缓存);
            设置.保存("安装包哈希", $记录);
        }

    }

    @Override
    public void 权限回调事件() {
        new 线程(this, "初始化").启动();
        初始化完成事件();
    }

    public void 初始化完成事件() {
        跳转脚本(文件.检查地址("$脚本/入口.js"));
        结束界面();
    }


}
