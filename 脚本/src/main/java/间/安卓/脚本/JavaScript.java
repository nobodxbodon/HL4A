package 间.安卓.脚本;

import org.mozilla.javascript.*;
import 间.接口.*;
import 间.安卓.工具.*;
import 间.安卓.脚本.事件.*;
import 间.工具.*;
import 间.收集.*;

public class JavaScript implements 基本脚本 {

    @Override
    public String 取脚本类型() {
        return "JavaScript";
    }

    public static 哈希表 替换关键字表 = new 哈希表();

    public static String[][] 默认替换表 = {
        {"出", "break"},
        {"选", "case"},
        {"续", "continue"},
        {"默", "default"},
        {"删", "delete"},
        {"执", "do"},
        {"另", "else"},
        {"假", "false"},
        {"循环", "for"},
        {"函数", "function"},
        {"如", "if"},
        {"在", "in"},
        {"变量", "let"},
        {"新", "new"},
        {"空", "null"},
        {"返", "return"},
        {"选", "switch"},
        {"此", "this"},
        {"真", "true"},
        {"型", "typeof"},
        {"自由变量", "var"},
        {"当", "while"},
        {"扩", "with"},
        {"让", "yield"},
        {"捕", "catch"},
        {"常量", "const"},
        {"终", "finally"},
        {"为", "instanceof"},
        {"抛", "throw"},
        {"试", "try"},

    };

    static {
        替换关键字(默认替换表);
    }

    private static String[] 工具类前缀 = {
        "间.安卓.插件","间.安卓.组件",
        "间.安卓.工具","间.安卓.弹窗",
        "间.安卓.网络","间.安卓.组件",
        "间.安卓.绘画","间.安卓.视图",
        "间.安卓.视图.扩展","间.安卓.视图.适配器",
       "间.安卓.视图.实现",
        "间.工具","间.数据","间.收集","间.接口",
        "java.lang","java.io","java.util",
        "android.os","android.util","android.content",
        "android.view","android.widget"
    };
    
    public static 哈希表<String,Class<?>> 类缓存表 = new 哈希表<>();
    public static 哈希表<String,Boolean> 类检查表 = new 哈希表<>();
    
    public static boolean 是工具类(String $名称) {
        if (类检查表.检查键值($名称)) {
            return 类检查表.读取($名称) == true;
        }
        Class<?> $类;
        for (String $单个 : 工具类前缀) {
            if (($类 = 反射.取类($单个 + "." + $名称))!= null) {
                类检查表.设置($名称,true);
                类缓存表.设置($名称,$类);
                return true;
            }
        }
        类检查表.设置($名称,false);
        return false;
    }
    
    public static Class<?> 找工具类(String $名称) {
        if (类缓存表.检查键值($名称)) {
            return 类缓存表.读取($名称);
        }
        Class<?> $类;
        for (String $单个 : 工具类前缀) {
            if (($类 = 反射.取类($单个 + "." + $名称))!= null) {
                类缓存表.设置($名称,$类);
                return $类;
            }
        }
        return null;
    }

    public static void 替换关键字(String $新,String $旧) {
        替换关键字表.设置($新, $旧);
    }

    public static void 替换关键字(String[][] $表) {
        for (String[] $单个 : $表) {
            替换关键字表.设置((String)$单个[0], (String)$单个[1]);
        }
    }

    public Context JS上下文;
    public Scriptable 函数环境;

    public JavaScript() {

        JS上下文 = Context.enter();
        JS上下文.setOptimizationLevel(-1);
        JS上下文.setLanguageVersion(Context.VERSION_ES6);
        ImporterTopLevel 初始化环境 = new ImporterTopLevel();
        初始化环境.initStandardObjects(JS上下文, false);
        函数环境 = 初始化环境;
        压入变量("当前环境", this);
        压入变量("是复制环境", false);
        压入变量("当前应用", 环境.取应用());
        执行代码(字符.读取(getClass().getClassLoader().getResourceAsStream("hl4a/android.js")));

    }

    public JavaScript(JavaScript $被继承) {

        JS上下文 = Context.enter();
        函数环境 =  $被继承.JS上下文.newObject($被继承.函数环境);
        函数环境.setParentScope(null);
        JS上下文.setOptimizationLevel(-1);
        JS上下文.setLanguageVersion(Context.VERSION_ES6);
        压入变量("复制环境", this);
        压入变量("是复制环境", true);

    }

    public JavaScript 置错误监听(方法 $警告,方法 $错误,方法 $运行时错误) {
        JS上下文.setErrorReporter(new 错误监听($警告, $错误, $运行时错误));
        return this;
    }

    @Override
    public void 压入变量(String $对象名,Object $对象) {
        try {
            ScriptableObject.putProperty(函数环境, $对象名, Context.javaToJS($对象, 函数环境));
        } catch (Exception $错误) {
            错误.抛出($错误);
        }
    }

    public void 压入常量(String $对象名,Object $对象) {
        try {
            ScriptableObject.putConstProperty(函数环境, $对象名, Context.javaToJS($对象, 函数环境));
        } catch (Exception $错误) {
            错误.抛出($错误);
        }
    }


    @Override
    public Object 读取对象(String $对象名) {
        Object $对象 = 函数环境.get($对象名, 函数环境);
        if ($对象 != Scriptable.NOT_FOUND) {
            return $对象;
        }
        return null;
    }


    public Function 读取函数(String $函数名) {
        Object $对象 = 读取对象($函数名);
        if ($对象 instanceof Function) {
            return (Function) $对象;
        }
        return null;
    }

    public Object 调用函数(Function $函数,Object... $传入) {
        if ($传入 == null) $传入 = new Object[]{};
        try {
            return $函数.call(JS上下文, 函数环境, 函数环境, $传入);
        } catch (Exception $错误) {
            错误.抛出($错误);
        }
        return null;
    }

    @Override
    public Object 调用函数(String $函数名,Object... $传入) {
        Function $函数 = 读取函数($函数名);
        if ($函数 != null)return 调用函数($函数, $传入);
        return null;
    }

    @Override
    public Object 执行代码(String $内容) {
        return 执行代码($内容, $内容);
    }

    public Script 编译代码(String $内容,String $环境名) {
        if ($内容 == null) $内容 = "";
        return JS上下文.compileString($内容, $环境名, 1, null);
    }

    public Object 执行代码(String $内容,String $环境名) {
        if ($内容 == null) $内容 = "";
        return JS上下文.evaluateString(函数环境, $内容.toString(), $环境名, 1, null);
    }

    @Override
    public Object 运行文件(String $地址) {
        String $ = 字符.读取($地址);
        return 执行代码($, $地址);
    }

    public Object 运行文件(String $地址,String $环境名) {
        String $ = 字符.读取($地址);
        return 执行代码($, $环境名);
    }

    public void 退出() {
        JS上下文.exit();
    }

}
