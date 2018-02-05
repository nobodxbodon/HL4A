package h.test.activity;

import android.os.Bundle;
import android.view.View;
import h.test.adapter.监控适配器;
import h.test.layout.布局_主界面;
import h.test.layout.布局_添加设备;
import h.test.util.监控信息;
import 间.安卓.弹窗.基本弹窗;
import 间.安卓.组件.基本界面;
import 间.接口.方法;
import 间.收集.哈希表;
import h.test.util.监控;
import 间.安卓.工具.提示;
import 间.安卓.工具.线程;
import 间.接口.调用;
import 间.安卓.工具.转换;
import 间.安卓.弹窗.加载中弹窗;
import 间.安卓.工具.处理;
import com.hikvision.netsdk.HCNetSDK;
import com.hikvision.netsdk.INT_PTR;
import 间.工具.反射;

public class MainActivity extends 基本界面 {

    private 布局_主界面 布局;
    private 布局_添加设备 弹窗布局;

    private 监控适配器 适配器;
    private 基本弹窗 新建弹窗;

    @Override
    public void 界面创建事件(Bundle $恢复) {
        打开布局(new 布局_主界面(this));
        布局 = (布局_主界面)当前视图;
        适配器 = new 监控适配器(this);
        
        布局.列表.置适配器(适配器);
        布局.列表.置项目单击事件($单击);
        弹窗布局 = new 布局_添加设备(this);
        新建弹窗 = new 基本弹窗(this);
        新建弹窗.置标题("添加设备");
        新建弹窗.置内容(弹窗布局);
        新建弹窗.置中按钮("取消", 新建弹窗.隐藏);
        新建弹窗.置右按钮("新建", 调用.代理(this, "新建"));
    }

    方法 $单击 = new 方法() {
        @Override
        public Object 调用(Object[] $参数) {
            int $键值 = $参数[2];
            if ($键值 == -1) {
                新建弹窗.显示();
            } else {
                View $项目 = (View)$参数[1];
                哈希表 $表 = (哈希表)$项目.getTag();
                监控信息 $信息 = (监控信息)$表.读取("信息");
                MainActivity.this.跳转界面(ViewActivity.class, $信息);
            }
            return null;
        }
    };

    public void 新建(Object[] $参数) {
        if (弹窗布局.地址.取文本().equals("")) {
            提示.警告("IP/域名不要留空 ~");
        } else if (弹窗布局.端口.取文本().equals("")) {
            提示.警告("端口不要留空 ~");
        } else if (弹窗布局.用户.取文本().equals("")) {
            提示.警告("用户名不要留空 ~");
        } else if (弹窗布局.密码.取文本().equals("")) {
            提示.警告("密码不要留空 ~");
        } else {
            监控信息 $信息 = new 监控信息();
            $信息.地址 = 弹窗布局.地址.取文本();
            $信息.端口 = 转换.数字(弹窗布局.端口.取文本());
            $信息.用户 = 弹窗布局.用户.取文本();
            $信息.密码 = 弹窗布局.密码.取文本();
            //监控.新建($信息);
            加载中弹窗 $进度 = new 加载中弹窗(this);
            $进度.显示();
            new 线程(this, "检查").启动($进度, $信息);
        }
    }

    public void 检查(加载中弹窗 $进度,监控信息 $信息) {
        $进度.更新("正在检查连接 ~");
        int $会话 = -1;
        if (($会话 = 监控.登录($信息)) != -1) {
            提示.普通("测试连接成功 ~ " + 监控.登出($会话));
            监控.新建($信息);
            $进度.隐藏();
            新建弹窗.隐藏();
            适配器.更新();
        } else {
            INT_PTR $错误 = new INT_PTR();
            反射.置变量($错误, "value", HCNetSDK.getInstance().NET_DVR_GetLastError());
            提示.警告("连接失败 ~ " + HCNetSDK.getInstance().NET_DVR_GetErrorMsg($错误));
            $进度.隐藏();
        }
        处理.主线程($进度.隐藏);
    }

}
