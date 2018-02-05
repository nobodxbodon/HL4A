package hnhnh.jk.layout;

import android.content.Context;
import 间.安卓.视图.线性布局;
import 间.安卓.视图.编辑框;
import 间.安卓.工具.主题;
import android.text.InputType;

public class 布局_添加设备 extends 线性布局 {
    
    public 编辑框 地址;
    public 编辑框 端口;
    public 编辑框 用户;
    public 编辑框 密码;
    
    public 布局_添加设备(Context $上下文) {
        super($上下文);
        int $填充 = 主题.取默认填充();
        置填充(0,0,$填充,$填充);
        地址 = new 编辑框(this);
        地址.置默认文本("设备IP地址/绑定的域名");
        地址.置输入类型("文本");
        端口 = new 编辑框(this);
        端口.置默认文本("设备端口");
        端口.置输入类型("数字");
        用户 = new 编辑框(this);
        用户.置默认文本("用户名");
        用户.置输入类型("文本");
        密码 = new 编辑框(this);
        密码.置默认文本("密码");
        密码.置输入类型("文本-密码");
    }
    
}

