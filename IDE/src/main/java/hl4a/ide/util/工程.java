package hl4a.ide.util;

import hl4a.ide.util.工程;
import hl4a.ide.util.工程信息;
import 间.安卓.工具.提示;
import 间.安卓.工具.文件;
import 间.工具.ZIP;
import 间.工具.字符;
import 间.工具.字节;
import 间.工具.编码;
import 间.数据.JSON;

public class 工程 {

    public static String 工程目录 = "%HL4A";
    public static String 配置文件 = "应用.json";

    public String 地址;
    public 工程信息 信息;

    public void 保存() {
        JSON.保存(转换地址(地址), 信息);
    }

    public static boolean 检查包名(String $内容) {
        if ($内容 == null || $内容.isEmpty())return false;
        String $前缀 = "包名规范(否则打包失败):\n";
        if (字符.是否出现($内容, " ")) {
            提示.警告($前缀 + "不能有空格");
            return false;
        }
        if (!字符.是否出现($内容, ".")) {
            提示.警告($前缀 + "必须有一个.号");
            return false;
        }
        if (字符.是否出现($内容, "..") || $内容.startsWith(".") || $内容.endsWith(".")) {
            提示.警告("包名规范(否则打包失败):\n两个.不能连续或不能以.开头/结尾");
            return false;
        }
        String[]$所有 = 字符.到数组(字符.替换($内容, ".", "/"), "/");
        for (String $单个 : $所有) {
            if ($单个.substring(0, 1).replaceAll("[0-9]", "").equals("")) {
                提示.警告("包名规范(否则打包失败):\n第一个字符不能为数字");
                return false;
            }
            if (!$单个.replaceAll("[a-z 0-9]", "").equals("")) {
                提示.警告("包名规范(否则打包失败):\n不能有特殊字符\n(包括大写字母/中文等)");
                return false;
            }
        }
        return true;
    }


    public static String 导入(String $文件) {
        if (!"hpk".equals(文件.取后缀($文件))) return "导入失败 不是HPK！";
        String $缓存 = 文件.取缓存目录("导入", "文件", 文件.取名称($文件));
        字节.保存($缓存, 编码.Base64.解码(字符.读取($文件)));
        String $目录 = 文件.取缓存目录("导入", "目录", 文件.取名称($文件));
        ZIP.解压($缓存, $目录);
        if (!文件.是文件($目录, "应用.json"))return "导入失败 HPK损坏 ！";
        工程信息 $信息 = (工程信息)JSON.读取($目录 + "/应用.json", 工程信息.class);
        if (工程.检查($信息.包名)) {
            return "导入失败 包名已占用~";
        }
        文件.复制($目录, 工程.工程目录 + "/" + $信息.包名);
        return "导入成功 ~";
    }

    public static 工程 读取(String $地址) {
        if (文件.是文件(转换地址($地址))) {
            工程 $工程 = new 工程();
            $工程.地址 = $地址;
            $工程.信息 = (工程信息)JSON.读取(转换地址($地址), 工程信息.class);
            return $工程;
        }
        return null;
    }

    public static boolean 移动(String $地址,String $新地址) {
        if (文件.是否存在(工程目录 + "/" + $新地址)) return false;
        if (文件.是文件(转换地址($地址))) {
            文件.剪切(工程目录 + "/" + $地址, 工程目录 + "/" + $新地址);
            return true;
        }
        return false;
    }

    public static 工程 读取目录(String $地址) {
        if (文件.是文件($地址)) {
            工程 $工程 = new 工程();
            $工程.地址 = $地址;
            $工程.信息 = (工程信息)JSON.读取(转换地址($地址), 工程信息.class);
            return $工程;
        }
        return null;
    }

    public static boolean 创建(String $工程名,String $包名) {
        if (检查($包名)) return false;
        工程 $工程 = new 工程();
        $工程.信息 = new 工程信息();
        $工程.地址 = $包名;
        $工程.信息.工程名 = $工程名;
        $工程.信息.包名 = $包名;
        $工程.保存();
        return true;
    }

    public static boolean 检查(String $地址) {
        if (文件.是文件(转换地址($地址))) {
            return true;
        }
        return false;
    }

    public static String 转换地址(String $地址) {
        return 工程目录 + "/" + $地址 + "/" + 配置文件;
    }

    public String 取地址() {
        return 工程目录 + "/" + 地址;
    }

    public String 取地址(String... $相对) {
        return 文件.取文件对象(工程目录 + "/" + 地址 + "/" + 字符.分解($相对, "/")).getPath();
    }

}
