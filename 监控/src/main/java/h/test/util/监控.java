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
        /*
        集合<监控信息> $所有 = new 集合<>();
        File[] $文件 = 文件.取文件列表(地址);
        for (File $单个 : $文件) {
            监控信息 $信息 = (监控信息)JSON.读取($单个.getPath(), 监控信息.class);
            if ($信息 == null) continue;
            $所有.添加($信息);
        }
         return $所有.到数组(监控信息.class);
        */
        String $地址 = "117.40.170.196";
        String $用户 = "admin";
        String $密码 = "a1234567";
        监控信息 $1 = new 监控信息();
        $1.地址 = $地址;
        $1.用户 = $用户;
        $1.密码 = $密码;
        $1.端口 = 9000;
        监控信息 $2 = new 监控信息();
        $2.地址 = $地址;
        $2.用户 = $用户;
        $2.密码 = $密码;
        $2.端口 = 9001;
        监控信息 $3 = new 监控信息();
        $3.地址 = $地址;
        $3.用户 = $用户;
        $3.密码 = $密码;
        $3.端口 = 9002;
        监控信息 $4 = new 监控信息();
        $4.地址 = $地址;
        $4.用户 = $用户;
        $4.密码 = $密码;
        $4.端口 = 9003;
        return new 监控信息[]{$1,$2,$3,$4};
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
        NET_DVR_DEVICEINFO_V30 $设备 = new NET_DVR_DEVICEINFO_V30();
        $信息.登录码 = HCNetSDK.getInstance().NET_DVR_Login_V30($信息.地址,$信息.端口,$信息.用户,$信息.密码,$设备);
        if ($设备.byChanNum > 0) {
            $信息.起始 = $设备.byStartChan;
            $信息.通道 = $设备.byChanNum;
        } else if ($设备.byIPChanNum > 0) {
            $信息.起始 = $设备.byStartDChan;
            $信息.通道 = $设备.byIPChanNum
                + $设备.byHighDChanNum * 256;
        }
        return $信息.登录码;
    }
    
    public static boolean 登出(int $会话) {
        return HCNetSDK.getInstance().NET_DVR_Logout_V30($会话);
    }

}
