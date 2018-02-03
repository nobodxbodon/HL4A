package h.test.util;

import java.io.File;
import 间.安卓.工具.文件;
import 间.工具.时间;
import 间.收集.集合;
import 间.数据.JSON;
import com.hikvision.netsdk.HCNetSDK;
import com.hikvision.netsdk.NET_DVR_DEVICEINFO_V30;

public class 监控 {

    public static final String 地址 = "$监控";

    public static 监控信息[] 所有() {
        集合<监控信息> $所有 = new 集合<>();
        File[] $文件 = 文件.取文件列表(地址);
        for (File $单个 : $文件) {
            监控信息 $信息 = (监控信息)JSON.读取($单个.getPath(), 监控信息.class);
            if ($信息 == null) continue;
            $所有.添加($信息);
        }
        return $所有.到数组(监控信息.class);
    }

    public static void 新建(监控信息 $信息) {
        JSON.保存(地址 + "/" + 时间.时间戳() + ".json", $信息);
    }

    public static boolean 初始化() {
        return HCNetSDK.getInstance().NET_DVR_Init();
    }
    
    public static boolean 释放() {
        return HCNetSDK.getInstance().NET_DVR_Cleanup();
    }
    
    public static boolean 置超时(int $超时) {
        return HCNetSDK.getInstance().NET_DVR_SetConnectTime($超时);
    }
    
    public static int 登录(监控信息 $信息) {
        return HCNetSDK.getInstance().NET_DVR_Login_V30($信息.地址,$信息.端口,$信息.用户,$信息.密码,new NET_DVR_DEVICEINFO_V30());
    }

}
