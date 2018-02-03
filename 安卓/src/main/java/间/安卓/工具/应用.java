package 间.安卓.工具;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import hl4a.runtime.ErrorActivity;
import java.util.List;
import 间.安卓.组件.基本应用;
import 间.安卓.组件.基本界面;
import 间.工具.字符;
import 间.工具.散列;
import 间.工具.时间;
import 间.工具.线程;
import 间.工具.错误;
import 间.接口.方法;
import 间.收集.集合;

public class 应用 {

    private static 集合<Activity> 所有界面 = new 集合<Activity>();

    public static boolean 已安装(String $包名) {
        PackageManager $PM = 环境.取应用().getPackageManager();
        try {
            $PM.getPackageInfo($包名, 0);
            return true;
        } catch (PackageManager.NameNotFoundException $错误) {
            return false;
        }
    }

    public static void 初始化界面(基本界面 $界面) {
        新建界面($界面);
        自动设置主题($界面);
    }

    public static void 新建界面(基本界面 $界面) {
        所有界面.添加($界面);
        竖屏($界面);
    }

    public static void 结束界面() {
        for (Activity $单个 : 所有界面) {
            $单个.finish();
        }
    }

    public static void 结束界面(Exception $错误) {
        for (Activity $单个 : 所有界面) {
            if ($单个 instanceof 基本界面)
                ((基本界面)$单个).结束界面($错误);
        }
    }


    public static 方法 错误处理 = new 方法() {
        @Override
        public Object 调用(Object[] $参数) {
            Exception $错误 = (Exception)$参数[1];
            应用.结束界面($错误);
            Intent $意图 = new Intent(环境.取应用(), ErrorActivity.class);
            $意图.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            $意图.putExtra("错误", "当前应用版本 :" + 应用.取版本名() + "\n" + 错误.取整个错误($错误));
            环境.取应用().startActivity($意图);
            字符.保存("#错误日志/" + 时间.格式() + ".log", 错误.取整个错误($错误));
            System.exit(0);
            return null;
        }
    };

    public static void 初始化应用(基本应用 $应用) {
        //System.setOut(new 打印处理(调用.代理(提示.class,"普通")));
        环境.置应用($应用);
        文件.初始化();
        线程.置错误处理(错误处理);
        主题.置圆角大小("3dp");
        主题.置大文本大小("8dp");
        主题.置文本大小("5dp");
        主题.置小文本大小("4dp");
        主题.置默认填充("16dp");
        主题.置中等填充("56dp");
        主题.置主题颜色(颜色.靛蓝);
        提示.初始化($应用);
    }

    public static void 自动设置主题(Context $上下文) {
        if (设备.取SDK() >= 21) {
            $上下文.setTheme(android.R.style.Theme_Material_Light_NoActionBar);
        } else {
            $上下文.setTheme(android.R.style.Theme_Holo_Light_NoActionBar);
        }
    }

    public static String 取安装包位置() {
        return 取安装包位置(环境.取应用().getPackageName());
    }

    public static String 取安装包位置(String $包名) {
        try {
            PackageManager $PM = 环境.取应用().getPackageManager();
            PackageInfo $INFO = $PM.getPackageInfo($包名, 0);
            return $INFO == null ? null : $INFO.applicationInfo.sourceDir;
        } catch (Exception $错误) {
            return null;
        }

    }

    public static void 启动(String $包名) {
        环境.取应用().startActivity(环境.取应用().getPackageManager().getLaunchIntentForPackage($包名));
    }

    public static String[] 取所有权限() {
        PackageInfo packageInfo = null;
        try {
            packageInfo = 环境.取应用().getPackageManager().getPackageInfo(
                应用.取包名(), PackageManager.GET_PERMISSIONS);
        } catch (Exception $错误) {}
        if (packageInfo != null) {
            return packageInfo.requestedPermissions;
        }
        return null;
    }

    public static 集合 取用户应用() {
        return 取所有应用(false);
    }

    public static 集合 取系统应用() {
        return 取所有应用(true);
    }

    public static 集合 取所有应用() {
        return 取所有应用(null);
    }

    public static 集合 取所有应用(Boolean $类型) {
        集合 $列表 = new 集合();
        PackageManager $PM = 环境.取应用().getPackageManager();  
        List<PackageInfo> $所有 = $PM.getInstalledPackages(0);
        for (PackageInfo $单个 : $所有) {
            if (($单个.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) > 0) {
                if (new Boolean(false).equals($类型)) continue;
            } else if (new Boolean(true).equals($类型)) { continue; }
            $列表.添加($单个.packageName);
        }
        return $列表;
    }

    public static String 取包名() {
        return 环境.取应用().getPackageName();
    }

    public static String 取应用名() {
        try {
            return 环境.取应用().getPackageManager().getPackageInfo(取包名(), 64).applicationInfo.loadLabel(环境.取应用().getPackageManager()).toString();
        } catch (Exception $错误) {}
        return null;
    }

    public static String 取版本名() {
        try {
            return 环境.取应用().getPackageManager().getPackageInfo(取包名(), 64).versionName;
        } catch (Exception $错误) {}
        return null;
    }

    public static Integer 取版本号() {
        try {
            return 环境.取应用().getPackageManager().getPackageInfo(取包名(), 64).versionCode;
        } catch (Exception $错误) {}
        return null;
    }

    public static String 取MD5签名() {
        try {
            return 散列.摘要("MD5", 环境.取应用().getPackageManager().getPackageInfo(取包名(), 64).signatures[0].toByteArray());
        } catch (Exception $错误) {}
        return null;
    }

    public static String 取SHA1签名() {
        try {
            return 散列.摘要("SHA-1", 环境.取应用().getPackageManager().getPackageInfo(取包名(), 64).signatures[0].toByteArray());
        } catch (Exception $错误) {}
        return null;
    }


    public static Drawable 取图标() {
        return 取图标(取包名());
    }


    public static Drawable 取图标(String $包名) {
        try {
            PackageManager pm = 环境.取应用().getPackageManager();
            ApplicationInfo info = pm.getApplicationInfo($包名, 0);
            return info.loadIcon(pm);
        } catch (Exception $错误) {}
        return null;
    }

    public static boolean 在后台() {
        ActivityManager am = (ActivityManager) 环境.取应用()
            .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskList = am.getRunningTasks(1);
        if (taskList != null && !taskList.isEmpty()) {
            ComponentName topActivity = taskList.get(0).topActivity;
            if (topActivity != null
                && !topActivity.getPackageName().equals(
                    环境.取应用().getPackageName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean 是横屏() {
        return 环境.取应用().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    public static int 取屏幕宽度() {
        DisplayMetrics dm = 环境.取应用().getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int 取屏幕高度() {
        DisplayMetrics dm = 环境.取应用().getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    public static float 取屏幕密度() {
        DisplayMetrics dm = 环境.取应用().getResources().getDisplayMetrics();
        return dm.density;
    }

    public static int 取屏幕DPI() {
        DisplayMetrics dm = 环境.取应用().getResources().getDisplayMetrics();
        return dm.densityDpi;
    }

    public static int 取状态栏高度(Activity $界面) {
        int $ = $界面.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return $界面.getResources().getDimensionPixelSize($);
    }

    public static void 全屏(Activity $界面) {
        $界面.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = $界面.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        window.setAttributes(params);
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    public static void 竖屏(Activity $界面) {
        $界面.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public static void 横屏(Activity $界面) {
        $界面.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

}
