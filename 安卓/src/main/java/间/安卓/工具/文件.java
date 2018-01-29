package 间.安卓.工具;

import 间.工具.*;
import android.os.*;
import android.content.*;
import android.net.*;
import android.webkit.MimeTypeMap;

public class 文件 extends 间.工具.文件 {

    protected static void 初始化() {
        文件.替换地址("%", 取存储目录(""));
        文件.替换地址("$", 取数据目录(""));
        文件.替换地址("#", 取存储目录("Android", "data", 应用.取包名(), ""));
    }

    public static void 打开(String $地址) {
        Intent $意图 = new Intent(Intent.ACTION_VIEW);
        $意图.setDataAndType(Uri.fromFile(文件.取文件对象($地址)), 取MIME($地址));
        $意图.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        环境.取应用().startActivity($意图);
    }
    
    public static String 取MIME(String $地址) {
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(取后缀($地址));
    }

    public static String 取存储目录() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    public static String 取存储目录(String... $地址) {
        return 取存储目录() + "/" + 字符.分解($地址, "/");
    }

    public static String 取数据目录() {
        return 环境.取应用().getFilesDir().getParent();
    }

    public static String 取数据目录(String... $地址) {
        return 取数据目录() + "/" + 字符.分解($地址, "/");
    }

    public static String 取下载缓存目录() {
        return Environment.getDownloadCacheDirectory().getPath();
    }
    
    public static String 取下载缓存目录(String... $地址) {
        return 取下载缓存目录() + "/" + 字符.分解($地址, "/");
    }

    public static String 取缓存目录() {
        return 环境.取应用().getCacheDir().getPath();
    }

    public static String 取缓存目录(String... $地址) {
        return 取数据目录() + "/" + 字符.分解($地址, "/");
    }

}
