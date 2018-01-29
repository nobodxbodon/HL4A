package hl4a.ide.activity;

import android.os.*;
import hl4a.ide.adapter.*;
import hl4a.ide.layout.*;
import hl4a.ide.util.*;
import 间.接口.*;
import 间.安卓.工具.*;
import 间.安卓.组件.*;
import 间.安卓.视图.*;
import 间.工具.*;
import 间.安卓.资源.布局.*;

public class MainActivity extends 基本界面 {

    布局_主页 布局;

    @Override
    public void 结束界面(Exception $错误) {
        //super.结束界面($错误);
    }
    
    @Override
    public void onCreate(Bundle $数据) {
        super.onCreate($数据);
        布局 = new 布局_主页(this);
        final 弹出菜单 $菜单 = 布局.标题.取菜单();
        
        $菜单.添加("帮助/手册", new 方法() {
                @Override
                public Object 调用(Object[] $参数) {
                    跳转界面(HelpActivity.class);
                    $菜单.隐藏();
                    return null;
                }
            });
        $菜单.添加("用户讨论群", new 方法() {
            @Override
                public Object 调用(Object[] $参数) {
                    链接工具.QQ.乐园部();
                    $菜单.隐藏();
                    return null;
                }
            });
        $菜单.添加("建议/反馈", new 方法() {
                @Override
                public Object 调用(Object[] $参数) {
                    链接工具.QQ.反馈();
                    $菜单.隐藏();
                    return null;
                }
            });
        
        布局.适配器 = new 工程适配器(布局.列表);
        布局.布局.置刷新事件(刷新);
        打开布局(布局);
        
            检查导入();
         测试();
    }
    
    public void 测试() {
       
    }

    public void 检查导入() {
        String $文件 = (String)传入参数[0];
        if ($文件 != null)
        提示工具.普通(工程.导入($文件));
        
    }

    @Override
    public void 界面刷新事件() {
        调用方法.事件(刷新);
    }


    方法 刷新 = new 方法() {
        @Override
        public Object 调用(Object[] $参数) {
            布局.适配器.更新工程();
            布局.布局.置刷新状态(false);
            return null;
        }
    };

}

