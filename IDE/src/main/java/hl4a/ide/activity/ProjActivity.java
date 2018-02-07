package hl4a.ide.activity;

import android.content.Intent;
import android.os.Bundle;
import hl4a.ide.layout.布局_工程管理;
import hl4a.ide.layout.布局_新建签名;
import hl4a.ide.layout.布局_设置弹窗;
import hl4a.ide.layout.布局_配置签名;
import hl4a.ide.util.工程;
import hl4a.ide.util.编译工程;
import 间.安卓.工具.提示;
import 间.安卓.工具.文件;
import 间.安卓.工具.线程;
import 间.安卓.工具.链接;
import 间.安卓.弹窗.基本弹窗;
import 间.安卓.组件.基本界面;
import 间.安卓.编译.秘钥签名;
import 间.安卓.视图.文本视图;
import 间.安卓.视图.线性布局;
import 间.安卓.资源.图标;
import 间.工具.ZIP;
import 间.工具.反射;
import 间.接口.方法;
import 间.接口.调用;
import 间.收集.哈希表;
import 间.安卓.编译.签名创建;
import 间.安卓.弹窗.加载中弹窗;
import 间.安卓.工具.处理;

public class ProjActivity extends 基本界面 {

    private 哈希表 所有 = new 哈希表();

    private 布局_工程管理 布局;
    private String 地址;
    private 工程 当前;

    private 基本弹窗 设置;
    private 基本弹窗 删除;
    private 基本弹窗 协议;
    private 基本弹窗 签名;
    private 基本弹窗 新建;

    private 布局_设置弹窗 内容;
    private 布局_配置签名 签名布局;
    private 布局_新建签名 新建布局;

    @Override
    public void 界面回调事件(int $请求码,int $返回码,Intent $意图) {
        switch ($返回码) {
            case 233:
                结束界面();return;
        }
    }

    @Override
    public void 结束界面(Exception $错误) {
        //super.结束界面($错误);
    }

    public boolean 检查() {
        if (!文件.是文件(当前.取地址(工程.配置文件))) {
            提示.警告("工程已损坏!");
            结束界面();
            return true;
        }
        return false;
    }

    @Override
    public void 界面刷新事件() {
        检查();
    }

    @Override
    public void onCreate(Bundle $数据) {
        super.onCreate($数据);
        地址 = (String)传入参数[0];
        if (!工程.检查(地址)) {
            提示.警告("工程已损坏!");
            结束界面();
        }
        当前 = 工程.读取(地址);
        布局 = new 布局_工程管理(this);
        布局.标题.左按钮(图标.返回, 界面结束);
        打开布局(布局);
        创建设置("工程名", "包名", "版本名", "版本号");
        内容 = new 布局_设置弹窗(this);
        设置 = new 基本弹窗(this);
        设置.置内容(内容);
        设置.置中按钮("取消", 设置.隐藏);
        删除 = new 基本弹窗(this);
        删除.置标题("删除工程");
        删除.置内容("真的要删除 " + 当前.信息.工程名 + " 吗？");
        删除.置中按钮("取消", 删除.隐藏);
        删除.置右按钮("删除", 直接删除);
        协议 = new 基本弹窗(this);
        协议.置标题("自由软件协议");
        协议.置内容("HL4A项目 基于GNU通用公共授权第三版\n您必须同意并在项目使用\nGNU公共授权才能制作独立应用");
        协议.置左按钮("详细", 调用.配置(链接.class, "打开", "http://www.gnu.org/licenses/gpl-3.0.html"));
        协议.置中按钮("拒绝", 协议.隐藏);
        协议.置右按钮("同意", 调用.配置(this, "打包APK"));
        签名布局 = new 布局_配置签名(this);
        签名 = new 基本弹窗(this);
        签名.置标题("配置签名");
        签名.置内容(签名布局);
        签名.置左按钮("新建", 调用.配置(this, "新建"));
        签名.置中按钮("取消", 签名.隐藏);
        签名.置右按钮("配置", 调用.配置(this, "配置"));
        新建布局 = new 布局_新建签名(this);
        新建 = new 基本弹窗(this);
        新建.置标题("新建签名");
        新建.置内容(新建布局);
        新建.置中按钮("取消", 新建.隐藏);
        新建.置右按钮("新建", 调用.配置(this, "新建签名"));
        进度 = new 加载中弹窗(this);
        进度.置可关闭(false);
        创建按钮("配置签名").置单击事件(调用.配置(this, "配置签名"));
        创建按钮("直接运行").置单击事件(直接运行);
        创建按钮("进入编辑").置单击事件(进入编辑);
        创建按钮("打包HPK").置单击事件(打包HPK);
        创建按钮("打包APK").置单击事件(协议.显示);
        创建按钮("删除工程").置单击事件(删除工程);
    }

    private 加载中弹窗 进度;

    public void 新建签名() {
        String $名称 = 新建布局.名称.取文本();
        String $别名 = 新建布局.别名.取文本();
        String $密码 = 新建布局.密码.取文本();
        String $别名密码 = 新建布局.别名密码.取文本();
        if ($别名.equals("")) {
            提示.警告("别名不要留空 ~");
            return;
        } else if (文件.是文件(当前.取地址(工程.秘钥目录 + "/" + $别名 + ".jks"))) {
            提示.警告("秘钥已存在！请检查");
            return;
        } else if ($密码.equals("")) {
            提示.警告("密码不要留空 ~");
            return;
        } else if ($别名密码.equals("")) {
            提示.警告("别名密码不要留空 ~");
            return;
        }
        进度.更新("正在加工 ~");
        进度.显示();
        new 线程(this, "线程新建").启动($名称, $别名, $密码, $别名密码);
    }

    public void 线程新建(String $名称,String $别名,String $密码,String $别名密码) {
        签名创建 $新建 = new 签名创建($别名, $密码, $别名密码);
        $新建.置名称($名称);
        $新建.创建(当前.取地址(工程.秘钥目录 + "/" + $别名 + ".jks"));
        进度.隐藏();
        提示.普通("新建成功 ~");
        处理.主线程(this, "新建回调", $别名, $密码, $别名密码);
    }

    public void 新建回调(String $别名,String $密码,String $别名密码) {
        签名布局.地址.置文本($别名 + ".jks");
        签名布局.密码.置文本($密码);
        签名布局.别名.置文本($别名);
        签名布局.别名密码.置文本($别名密码);
        新建.隐藏();
    }

    public void 配置签名() {
        签名布局.地址.置文本(当前.信息.秘钥地址);
        签名布局.密码.置文本(当前.信息.秘钥密码);
        签名布局.别名.置文本(当前.信息.秘钥别名);
        签名布局.别名密码.置文本(当前.信息.秘钥别名密码);
        签名.显示();
    }

    public void 新建() {
        新建.show();
    }

    public void 配置() {
        String $地址 = 签名布局.地址.取文本();
        if (!文件.是文件(当前.取地址(工程.秘钥目录, $地址))) {
            提示.警告("秘钥不存在 ~");
            return;
        }
        new 线程(this, "加载秘钥").启动($地址);
    }

    public void 加载秘钥(String $地址) {
        String $密码 = 签名布局.密码.取文本();
        String $别名 = 签名布局.别名.取文本();
        String $别名密码 = 签名布局.别名密码.取文本();
        try {
            new 秘钥签名(当前.取地址(工程.秘钥目录, $地址), $密码, $别名, $别名密码);
        } catch (Exception e) {
            提示.警告(e.getMessage());
            return;
        }
        当前.信息.秘钥地址 = $地址;
        当前.信息.秘钥密码 = $密码;
        当前.信息.秘钥别名 = $别名;
        当前.信息.秘钥别名密码 = $别名密码;
        当前.保存();
        提示.普通("配置成功 ~");
        签名.隐藏();
    }

    方法 直接运行 = new 方法() {
        @Override
        public Object 调用(Object[] $参数) {
            if (检查()) return null;
            String $入口 = 当前.取地址(工程.源码目录, "入口.js");
            if (!文件.是文件($入口)) {
                提示.普通("没有入口文件 ！");
            } else {
                try {
                跳转脚本($入口);
                }catch(Exception $错误) {}
            }
            return null;
        }
    };

    方法 打包HPK = new 方法() {
        @Override
        public Object 调用(Object[] $参数) {
            if (检查()) return null;
            String $输出 = 文件.取目录(当前.取地址()) + "/" + 当前.信息.工程名 + ".hpk";
            ZIP.压缩(当前.取地址(), $输出);
            提示.普通("打包成功 ~ \n" + $输出);
            return null;
        }
    };

    public void 打包APK(Object[] $参数) {
        if (检查()) return;
        协议.隐藏();
        if (!工程.检查包名(当前.信息.包名)) {
            内容.类型 = "包名";
            设置.置标题("包名不符合规范");
            内容.编辑.置默认文本("新的包名");
            设置.置右按钮("打包", 更改打包);
            设置.显示();
            return;
        }

        try {
            秘钥签名 $签名 = new 秘钥签名(当前.取地址(工程.秘钥目录, 当前.信息.秘钥地址), 当前.信息.秘钥别名, 当前.信息.秘钥密码, 当前.信息.秘钥别名密码);
            new 编译工程(ProjActivity.this, 当前, $签名).启动();
        } catch (Exception e) {
            提示.警告(e.getMessage());
            配置签名();
        }
    }

    方法 删除工程 = new 方法() {
        @Override
        public Object 调用(Object[] $参数) {
            删除.显示();
            return null;
        }
    };

    方法 直接删除 = new 方法() {
        @Override
        public Object 调用(Object[] $参数) {
            if (检查())return null;
            文件.删除(当前.取地址());
            提示.普通("删除成功 ！");
            删除.隐藏();
            结束界面();
            return null;
        }
    };

    方法 进入编辑 = new 方法() {
        @Override
        public Object 调用(Object[] $参数) {
            if (检查())return null;
            跳转界面(666, EditActivity.class, 当前.地址);
            return null;
        }
    };

    方法 更改打包 = new 方法() {
        @Override
        public Object 调用(Object[] $参数) {
            if (更改设置.调用() == null) return null;
            打包APK(null);
            return null;
        }
    };

    方法 更改设置 = new 方法() {
        @Override
        public Object 调用(Object[] $参数) {
            if (检查())return null;
            String $内容 = 内容.编辑.取文本();
            String $类型 = 内容.类型;
            String $原内容 = (String)反射.取变量(当前.信息, $类型);
            if ("".equals($内容)) {
                提示.警告("请不要留空 ~");
                return null;
            } else if ($类型 == "包名") {
                if (!工程.检查包名($内容)) {
                    return null;
                } else if (工程.检查($内容)) {
                    提示.警告("该包名已存在 ~");
                    return null;
                }
                if (!工程.移动($原内容, $内容)) {
                    提示.警告("移动失败 未知错误!");
                    return null;
                }
                地址 = $内容;
                当前.地址 = $内容;
            } else {
                提示.普通("更改成功 ~");
            }
            ((文本视图)所有.读取($类型)).置文本($内容);
            反射.置变量(当前.信息, $类型, $内容);
            当前.保存();
            设置.隐藏();
            return 233;
        }
    };

    方法 界面结束 = new 方法() {
        @Override
        public Object 调用(Object[] $参数) {
            结束界面();
            return null;
        }
    };

    方法 启动设置 = new 方法() {
        @Override
        public Object 调用(Object[] $参数) {
            if (检查())return null;
            线性布局 $按钮 = (线性布局)$参数[0];
            文本视图 $文本 = (文本视图)$按钮.取子元素("文本");
            文本视图 $内容 = (文本视图)$按钮.取子元素("内容");
            内容.类型 = $文本.取文本();
            内容.编辑.置文本($内容.取文本());
            内容.编辑.置默认文本("请输入新" + 内容.类型);
            设置.置标题("更改" + $文本.取文本());
            设置.置右按钮("更改", 更改设置);
            设置.显示();
            return null;
        }
    };

    public void 创建设置(String... $所有) {
        for (String $设置 :$所有) {
            线性布局 $按钮 = new 线性布局(布局.底层);
            $按钮.置高度("自动");
            $按钮.置背景("透明");
            $按钮.置方向("水平");
            $按钮.置重力("中间垂直");
            $按钮.置单击事件(启动设置);
            $按钮.置填充("16dp", "16dp", "16dp", "16dp");
            文本视图 $文本 = new 文本视图($按钮);
            $文本.置文本($设置);
            $文本.置标签("文本");
            线性布局 $布局 = new 线性布局($按钮);
            $布局.置方向("水平");
            $布局.置重力("右边");
            $布局.置高度("自动");
            文本视图 $内容 = new 文本视图($布局);
            $内容.置标签("内容");
            $内容.置文本(反射.取变量(当前.信息, $设置).toString());
            所有.设置($设置, $内容);
        }
    }

    public 线性布局 创建按钮(String $名称) {
        线性布局 $按钮 = new 线性布局(布局.底层);
        $按钮.置高度("自动");
        $按钮.置背景("透明");
        $按钮.置方向("水平");
        $按钮.置重力("中间垂直");
        $按钮.置填充("16dp", "16dp", "16dp", 0);
        文本视图 $文本 = new 文本视图($按钮);
        $文本.置文本($名称);
        $文本.置标签("文本");
        return $按钮;
    }



}
