package 放课后乐园部.安卓.工具;

import android.app.*;
import 放课后乐园部.安卓.组件.*;

public class 上下文工具 {

    private static Application 全局上下文;

    public static void 置全局上下文(Application $上下文) {
        全局上下文 = $上下文;
    }

    public static Application 取全局上下文() {
        return 全局上下文;
    }

    public static 基本应用 取应用() {
        return (基本应用)全局上下文;
    }
    
}
