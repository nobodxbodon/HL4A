package hl4a.ide.layout;

import android.content.*;
import hl4a.ide.adapter.*;
import 间.安卓.视图.*;
import 间.安卓.资源.布局.*;
import 间.安卓.工具.*;
import 间.安卓.视图.适配器.*;
import 间.安卓.视图.扩展.*;

public class 布局_主页 extends 布局_基本界面 {


    public 高级滑动 滑动;
    
    public 下拉刷新布局 布局;
    public 列表视图 列表;
    public 工程适配器 适配器;

    public 布局_主页(final Context $上下文) {
        super($上下文);
        标题.置阴影(0);
        布局 = new 下拉刷新布局($上下文);
        列表 = new 列表视图(布局);
        滑动 = new 高级滑动(底层);
        //滑动.添加("桌面",new 线性布局($上下文));
        滑动.添加("分享",new 线性布局($上下文));
        滑动.添加("开发",布局);
        //滑动.添加("设置",new 线性布局($上下文));
    }

}
