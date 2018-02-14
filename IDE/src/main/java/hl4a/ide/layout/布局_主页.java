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

    public 列表视图 列表;

    public 列表视图 签名;

    public 布局_主页(final Context $上下文) {
        super($上下文);
        标题.置阴影(0);
        滑动 = new 高级滑动(底层);
        列表 = new 列表视图($上下文);
        //滑动.添加("桌面",new 线性布局($上下文));
        //滑动.添加("分享",new 线性布局($上下文));
        签名 = new 列表视图($上下文);
        滑动.添加("工程", 列表);
        滑动.添加("签名", 签名);
        //滑动.添加("设置",设置);
        //滑动.添加("关于",工程);
    }

}
