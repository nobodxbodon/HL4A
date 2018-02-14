package hl4a.ide.activity;

import android.os.Bundle;
import hl4a.ide.adapter.工程适配器;
import hl4a.ide.layout.布局_主页;
import hl4a.ide.util.工程;
import 间.安卓.工具.提示;
import 间.安卓.工具.链接;
import 间.安卓.组件.基本界面;
import 间.安卓.视图.弹出菜单;
import 间.接口.方法;
import 间.接口.调用;
import hl4a.ide.adapter.签名适配器;

public class MainActivity extends 基本界面 {

    private 布局_主页 布局;
    private 工程适配器 适配器;
    private 签名适配器 签名;

    @Override
    public void 结束界面(Exception $错误) {
        //super.结束界面($错误);
    }

    @Override
    public void onCreate(Bundle $数据) {
        super.onCreate($数据);
        打开布局(new 布局_主页(this));
        布局 = (布局_主页)当前视图;
        适配器 = new 工程适配器(布局.列表);
        签名 = new 签名适配器(布局.签名);
        检查导入();
    }

    public void 检查导入() {
        String $文件 = (String)传入参数[0];
        if ($文件 != null)
            提示.普通(工程.导入($文件));
    }

    @Override
    public void 界面刷新事件() {
        适配器.更新工程();
    }


}

