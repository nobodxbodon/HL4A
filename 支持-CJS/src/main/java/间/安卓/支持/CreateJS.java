package 间.安卓.支持;

import android.app.Activity;
import 间.安卓.工具.应用;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.NativeJavaPackage;

public class CreateJS {
    
    public static NativeJavaPackage init(Scriptable $环境) {
        Activity $界面 = (Activity)$环境.get("libs_inthis",$环境);
        应用.初始化应用($界面.getApplication());
        应用.初始化界面($界面);
        return new NativeJavaPackage("间",CreateJS.class.getClassLoader());
    }
    
}
