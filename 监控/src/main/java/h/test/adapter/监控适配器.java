package h.test.adapter;

import android.content.Context;
import java.io.File;
import 间.安卓.工具.文件;
import 间.安卓.视图.适配器.数组适配器;
import 间.数据.JSON;
import h.test.util.监控信息;
import 间.收集.哈希表;
import h.test.util.监控;

public class 监控适配器 extends 数组适配器 {

    public 监控适配器(Context $上下文) {
        super($上下文, new String[0]);
        更新();
    }

    public void 更新() {
        监控信息[] $信息 = 监控.所有();
        哈希表 $数据 = new 哈希表() ;
        $数据.设置("内容", "添加设备");
        数据.添加($数据);
        for (监控信息 $单个 : $信息) {
            添加监控($单个);
        }
    }

    private void 添加监控(监控信息 $信息) {
        哈希表 $参数 = new 哈希表();
        $参数.设置("信息", $信息);
        $参数.设置("内容", $信息.地址 + " : " + $信息.端口);
        数据.添加($参数);
    }

}
