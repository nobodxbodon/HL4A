package hl4a.ide.layout;

import android.content.*;
import 间.接口.*;
import 间.安卓.视图.*;
import 间.安卓.视图.扩展.*;
import 间.安卓.视图.适配器.*;
import 间.安卓.资源.布局.*;
import 间.安卓.资源.图标;

public class 布局_编辑界面 extends 布局_侧滑界面 {
    
    public 标题栏 选中标题;
    public 符号栏 符号;
    public 代码框 代码;
    public 列表视图 列表;
    public 文件适配器 适配器;
    
    public 布局_编辑界面(Context $上下文) {
        super($上下文);
        标题.左按钮(打开侧滑);
        选中标题 = new 标题栏(底层);
        选中标题.置标题("选择");
        选中标题.隐藏();
        代码 = new 代码框(底层);
        滚动视图 $滚动 = new 滚动视图(底层);
        $滚动.置高度("自动");
        符号 = new 符号栏($滚动);
        代码.置布局权重(1);
        符号.置代码框(代码);
        列表 = new 列表视图(侧滑);
        选中标题.右按钮(图标.全选,调用.配置(代码,"全选"));
        选中标题.右按钮(图标.剪切,调用.配置(代码,"剪切"));
        选中标题.右按钮(图标.复制,调用.配置(代码,"复制"));
        选中标题.右按钮(图标.粘贴,调用.配置(代码,"粘贴"));
    }
    
    方法 打开侧滑 = new 方法() {
        @Override
        public Object 调用(Object[] $参数) {
            打开侧滑();
            return null;
        }
    };
    
}
