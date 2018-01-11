package h.ide.util;
import 放课后乐园部.基本.*;
import 放课后乐园部.数据.*;

public class 工程 {

    public static String 工程目录 = "%HL4A";
    public static String 配置文件 = "应用.json";

    public String 配置;
    public 工程信息 信息;
    
    public void 保存() {
        字符.保存(转换地址(配置),Json.转换(信息));
    }
    
    public static 工程 读取(String $地址) {
        工程 $工程 = new 工程();
        $工程.信息 = (工程信息)Json.解析类(字符.读取(转换地址($地址)),工程信息.class);
        $工程.保存();
        return $工程;
    }
    
    public static boolean 创建(String $工程名,String $包名) {
        if (检查($包名)) return false;
        工程 $工程 = new 工程();
        $工程.信息 = new 工程信息();
        $工程.配置 = $包名;
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

}
