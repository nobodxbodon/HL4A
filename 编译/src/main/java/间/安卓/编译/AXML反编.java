package 间.安卓.编译;

import android.content.res.*;
import 间.工具.*;
import android.content.res.chunk.types.*;
import java.io.*;

public class AXML反编 {

    public AXMLResource $源码;
    
    public AXML反编(String $地址) {
        $源码 = new AXMLResource();
        try {
            $源码.read(流.输入.文件($地址));
        } catch (Exception $错误) {}
    }
    
    public String 读取() {
        return $源码.toXML();
    }
    
    public void 保存(String $地址) {
        字符.保存($地址,读取());
    }
    
    public void 编译(String $地址) {
        try {
            $源码.write(流.输出.文件($地址));
        } catch (IOException $错误) {}
    }

    
}
