package 间.安卓.工具;

import android.app.Application;
import 间.安卓.组件.基本应用;
import 间.收集.哈希表;

public class 环境 {

    private static 哈希表<Object,Object> 环境表 = new 哈希表<>();

    public static Object 读取(Object $键值) {
        synchronized (环境表) {
            return 环境表.读取($键值);
        }
    }

    public static synchronized void 设置(Object $键值,Object $内容) {
        synchronized (环境表) {
            环境表.设置($键值, $内容);
        }
    }

    public static void 置应用(Application $上下文) {
        设置("应用", $上下文);
    }

    public static Application 取应用() {
        return (Application)读取("应用");
    }

    public static 基本应用 取基本应用() {
        Application $应用 = 取应用();
        if ($应用 instanceof 基本应用) {
            return (基本应用)$应用;
        }
        return null;
    }

}
