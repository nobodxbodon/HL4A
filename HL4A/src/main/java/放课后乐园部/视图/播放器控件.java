package 放课后乐园部.视图;

import android.app.*;
import android.content.*;
import android.view.*;
import android.widget.*;
import 放课后乐园部.事件.*;
import 放课后乐园部.基本.*;
import 放课后乐园部.视图.实现.*;
import android.net.*;
import java.net.*;

public class 播放器控件 extends VideoView implements 基本视图 {

    MediaController 控制器;
    String 视频地址;

    public 播放器控件(Context $上下文) {
        super($上下文);
        控制器 = new MediaController($上下文);
        setMediaController(控制器);
        视图实现.初始化控件(this);
        置主题("默认");
    }
    
    public 播放器控件 开始() {
        置播放状态("开始");
        return this;
    }
    
    public 播放器控件 暂停() {
        置播放状态("暂停");
        return this;
    }
    
    public 播放器控件 停止() {
        置播放状态("停止");
        return this;
    }
    
    
    public 播放器控件 置播放状态(String $状态) {
        
        switch ($状态) {
            case "开始":start();break;
            case "暂停":pause();break;
            case "倒带":
            break;
        }
        
        return this;
        
    }

    public 播放器控件 置视频地址(String $地址) {
        if (文件.是文件($地址)) {
            视频地址 = 文件.检查地址($地址);
            setVideoPath(视频地址);
        } else if (字符.以开始($地址, "http")) {
            视频地址 = $地址;
            setVideoURI(Uri.parse(视频地址));
        }
        return this;
    }

    public String 取视频地址() {
        return 视频地址;
    }
    
    public int 取视频长度() {
        return getDuration();
    }
    
    public int 取播放位置() {
        return getCurrentPosition();
    }
    
    public 播放器控件 置播放位置(int $位置) {
        seekTo($位置);
        return this;
    }
    
    public boolean 是正在播放() {
        return isPlaying();
    }
    
    public 播放器控件 重新开始() {
        resume();
        return this;
    }

    @Override
    public 播放器控件 加入到(ViewGroup $布局) {
        视图实现.加入到(this, $布局);
        return this;
    }

    @Override
    public 播放器控件 打开(Activity $界面) {
        视图实现.打开(this, $界面);
        return this;
    }

    @Override
    public 播放器控件 置标签(Object $标签) {
        视图实现.置标签(this, $标签);
        return this;
    }

    @Override
    public Object 取标签() {
        return 视图实现.取标签(this);
    }

    @Override
    public 播放器控件 置主题(String $主题) {
        视图实现.置主题(this, $主题);
        return this;
    }

    @Override
    public 播放器控件 置单击事件(通用方法 $事件) {
        视图实现.置单击事件(this, $事件);
        return this;
    }

    @Override
    public 播放器控件 置长按事件(通用方法 $事件) {
        视图实现.置长按事件(this, $事件);
        return this;
    }

    @Override
    public 播放器控件 置触摸事件(通用方法 $事件) {
        视图实现.置触摸事件(this, $事件);
        return this;
    }

    @Override
    public 播放器控件 置宽度(Object $宽度) {
        视图实现.置宽度(this, $宽度);
        return this;
    }

    @Override
    public 播放器控件 置高度(Object $高度) {
        视图实现.置高度(this, $高度);
        return this;
    }

    @Override
    public 播放器控件 置状态(String $状态) {
        视图实现.置状态(this, $状态);
        return this;
    }

    @Override
    public String 取状态() {
        return 视图实现.取状态(this);
    }

    @Override
    public 播放器控件 显示() {
        视图实现.显示(this);
        return this;
    }

    @Override
    public 播放器控件 占位() {
        视图实现.占位(this);
        return this;
    }

    @Override
    public 播放器控件 隐藏() {
        视图实现.隐藏(this);
        return this;
    }

    @Override
    public 播放器控件 置边距(Object $边距) {
        视图实现.置边距(this, $边距);
        return this;
    }

    @Override
    public 播放器控件 置边距(Object $上,Object $下,Object $左,Object $右) {
        视图实现.置边距(this, $上, $下, $左, $右);
        return this;
    }

    @Override
    public 播放器控件 置上边距(Object $边距) {
        视图实现.置上边距(this, $边距);
        return this;
    }

    @Override
    public 播放器控件 置下边距(Object $边距) {
        视图实现.置下边距(this, $边距);
        return this;
    }

    @Override
    public 播放器控件 置左边距(Object $边距) {
        视图实现.置左边距(this, $边距);
        return this;
    }

    @Override
    public 播放器控件 置右边距(Object $边距) {
        视图实现.置右边距(this, $边距);
        return this;
    }

    @Override
    public 播放器控件 置填充(Object $填充) {
        视图实现.置填充(this, $填充);
        return this;
    }

    @Override
    public 播放器控件 置填充(Object $上,Object $下,Object $左,Object $右) {
        视图实现.置填充(this, $上, $下, $左, $右);
        return this;
    }

    @Override
    public 播放器控件 置上填充(Object $填充) {
        视图实现.置上填充(this, $填充);
        return this;
    }

    @Override
    public 播放器控件 置下填充(Object $填充) {
        视图实现.置下填充(this, $填充);
        return this;
    }

    @Override
    public 播放器控件 置左填充(Object $填充) {
        视图实现.置左填充(this, $填充);
        return this;
    }

    @Override
    public 播放器控件 置右填充(Object $填充) {
        视图实现.置右填充(this, $填充);
        return this;
    }

    @Override
    public 播放器控件 置背景(Object $背景) {
        视图实现.置背景(this, $背景);
        return this;
    }

    @Override
    public 播放器控件 置背景颜色(Object $颜色) {
        视图实现.置背景颜色(this, $颜色);
        return this;
    }


}
