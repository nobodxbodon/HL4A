package hl4a.ide.activity;

import android.os.Bundle;
import hl4a.ide.layout.布局_帮助文档;
import java.io.File;
import 间.安卓.工具.应用;
import 间.安卓.工具.文件;
import 间.安卓.组件.基本界面;
import 间.安卓.视图.适配器.数组适配器;
import 间.安卓.资源.布局.布局_适配器_数组;
import 间.工具.ZIP;
import 间.工具.字符;
import 间.接口.方法;
import 间.收集.集合;
import 间.安卓.工具.提示;

public class HelpActivity extends 基本界面 {

    public String 地址 = "%HL4A/帮助";
    public 布局_帮助文档 布局;
    public 数组适配器 适配器;

    @Override
    public void 界面创建事件(Bundle $恢复) {
        布局 = new 布局_帮助文档(this);
        ZIP.解压(应用.取安装包位置(),"assets/help.zip","$cache/帮助.zip");
        ZIP.解压("$cache/帮助.zip",地址);
        集合<String> $显示 = new 集合<>();
        File[] $所有 = 文件.取文件列表(地址);
        for (File $单个 : $所有) {
            String $名称 = 文件.取前缀($单个.getPath());
            $显示.添加($名称);
        }
        适配器 = new 数组适配器(this, $显示.toArray(new String[$显示.数量()]));
        布局.列表.置适配器(适配器);
        布局.列表.置项目单击事件($列表单击);
        打开布局(布局);
    }

    方法 $列表单击 = new 方法() {
        @Override
        public Object 调用(Object[] $参数) {
            String $内容 = ((布局_适配器_数组)$参数[1]).文本.取文本();
            String $地址 = 地址 + "/" + $内容 + ".txt";
            提示.普通(文件.是文件($地址));
            跳转界面(ViewActivity.class, $内容, 字符.读取($地址));
            return null;
        }
    };

    @Override
    public Boolean 返回按下事件() {
        结束界面();
        return true;
    }

}

