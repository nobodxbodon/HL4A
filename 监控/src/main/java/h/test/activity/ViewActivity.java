package h.test.activity;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import com.hikvision.netsdk.HCNetSDK;
import com.hikvision.netsdk.NET_DVR_DEVICEINFO_V30;
import com.hikvision.netsdk.NET_DVR_PREVIEWINFO;
import com.hikvision.netsdk.RealPlayCallBack;
import h.test.util.监控;
import h.test.util.监控信息;
import org.MediaPlayer.PlayM4.Player;
import 间.安卓.工具.提示;
import 间.安卓.弹窗.列表弹窗;
import 间.安卓.组件.基本界面;
import 间.安卓.资源.布局.布局_适配器_数组;
import 间.接口.调用;
import 间.安卓.工具.线程;
import android.widget.FrameLayout;
import android.content.pm.ActivityInfo;
import 间.安卓.资源.布局.布局_基本界面;
import 间.安卓.资源.图标;
import 间.接口.错误处理;
import 间.工具.错误;

public class ViewActivity extends 基本界面 implements Callback {

    private 监控信息 信息;

    private SurfaceView 播放 = null;

    private int m_iLogID = -1; // return by NET_DVR_Login_v30
    private int m_iPlayID = -1; // return by NET_DVR_RealPlay_V30
    private int m_iPlaybackID = -1; // return by NET_DVR_PlayBackByTime

    private int m_iPort = -1; // play port
    private int m_iStartChan = 0; // start channel no
    private int m_iChanNum = 0; // channel number

    private final String TAG = "DemoActivity";

    private boolean m_bTalkOn = false;
    private boolean m_bPTZL = false;

    private boolean m_bNeedDecode = true;
    private boolean m_bSaveRealData = false;
    private boolean m_bStopPlayback = false;

    public void surfaceCreated(SurfaceHolder holder) {
        is = true;
        播放.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        Log.i(TAG, "surface is created" + m_iPort);
        if (-1 == m_iPort) {
            return;
        }
        Surface surface = holder.getSurface();
        if (true == surface.isValid()) {
            if (false == Player.getInstance()
                .setVideoWindow(m_iPort, 0, holder)) {
                Log.e(TAG, "Player setVideoWindow failed!");
            }
        }
    }

    // @Override
    public void surfaceChanged(SurfaceHolder holder,int format,int width,
                               int height) {
    }

    // @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i(TAG, "Player setVideoWindow release!" + m_iPort);
        if (-1 == m_iPort) {
            return;
        }
        if (true == holder.getSurface().isValid()) {
            if (false == Player.getInstance().setVideoWindow(m_iPort, 0, null)) {
                Log.e(TAG, "Player setVideoWindow failed!");
            }
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("m_iPort", m_iPort);
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        m_iPort = savedInstanceState.getInt("m_iPort");
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState");
    }

    /**
     * @fn initeSdk
     * @author zhuzhenlei
     * @brief SDK init

     *            [out]
     * @return true - success;false - fail
     */

    // get controller instance

    private boolean startSinglePreview() {
        if (m_iPlaybackID >= 0) {
            Log.i(TAG, "Please stop palyback first");
            return false;
        }
        RealPlayCallBack fRealDataCallBack = getRealPlayerCbf();
        if (fRealDataCallBack == null) {
            Log.e(TAG, "fRealDataCallBack object is failed!");
            return false;
        }
        Log.i(TAG, "m_iStartChan:" + m_iStartChan);

        NET_DVR_PREVIEWINFO previewInfo = new NET_DVR_PREVIEWINFO();
        previewInfo.lChannel = m_iStartChan;
        previewInfo.dwStreamType = 0; // substream
        previewInfo.bBlocked = 1;
//         NET_DVR_CLIENTINFO struClienInfo = new NET_DVR_CLIENTINFO();
//         struClienInfo.lChannel = m_iStartChan;
//         struClienInfo.lLinkMode = 0;
        // HCNetSDK start preview
        m_iPlayID = HCNetSDK.getInstance().NET_DVR_RealPlay_V40(m_iLogID,
                                                                previewInfo, fRealDataCallBack);
//         m_iPlayID = HCNetSDK.getInstance().NET_DVR_RealPlay_V30(m_iLogID,
//         struClienInfo, fRealDataCallBack, false);
        if (m_iPlayID < 0) {
            Log.e(TAG, "NET_DVR_RealPlay is failed!Err:"
                  + HCNetSDK.getInstance().NET_DVR_GetLastError());
            return false;
        }


        Log.i(TAG,
              "NetSdk Play sucess ***********************3***************************");
        return true;
    }

    private void stopSinglePreview() {
        if (m_iPlayID < 0) {
            Log.e(TAG, "m_iPlayID < 0");
            return;
        }

        // net sdk stop preview
        if (!HCNetSDK.getInstance().NET_DVR_StopRealPlay(m_iPlayID)) {
            Log.e(TAG, "StopRealPlay is failed!Err:"
                  + HCNetSDK.getInstance().NET_DVR_GetLastError());
            return;
        }

        m_iPlayID = -1;
        stopSinglePlayer();
    }

    private void stopSinglePlayer() {
        Player.getInstance().stopSound();
        // player stop play
        if (!Player.getInstance().stop(m_iPort)) {
            Log.e(TAG, "stop is failed!");
            return;
        }

        if (!Player.getInstance().closeStream(m_iPort)) {
            Log.e(TAG, "closeStream is failed!");
            return;
        }
        if (!Player.getInstance().freePort(m_iPort)) {
            Log.e(TAG, "freePort is failed!" + m_iPort);
            return;
        }
        m_iPort = -1;
    }

    /**
     * @fn loginNormalDevice
     * @author zhuzhenlei
     * @brief login on device

     *            [out]
     * @return login ID
     */


    private RealPlayCallBack getRealPlayerCbf() {
        RealPlayCallBack cbf = new RealPlayCallBack() {
            public void fRealDataCallBack(int iRealHandle,int iDataType,
                                          byte[] pDataBuffer,int iDataSize) {
                // player channel 1
                ViewActivity.this.processRealData(1, iDataType, pDataBuffer,
                                                  iDataSize, Player.STREAM_REALTIME);
            }
        };
        return cbf;
    }

    private boolean is = false;

    public void processRealData(int iPlayViewNo,int iDataType,
                                byte[] pDataBuffer,int iDataSize,int iStreamMode) {
        if (!m_bNeedDecode) {
            // Log.i(TAG, "iPlayViewNo:" + iPlayViewNo + ",iDataType:" +
            // iDataType + ",iDataSize:" + iDataSize);
        } else {
            if (HCNetSDK.NET_DVR_SYSHEAD == iDataType) {
                if (m_iPort >= 0) {
                    return;
                }
                m_iPort = Player.getInstance().getPort();
                if (m_iPort == -1) {
                    Log.e(TAG, "getPort is failed with: "
                          + Player.getInstance().getLastError(m_iPort));
                    return;
                }
                Log.i(TAG, "getPort succ with: " + m_iPort);
                if (iDataSize > 0) {
                    if (!Player.getInstance().setStreamOpenMode(m_iPort,
                                                                iStreamMode)) {
                        Log.e(TAG, "setStreamOpenMode failed");
                        return;
                    }
                    if (!Player.getInstance().openStream(m_iPort, pDataBuffer,
                                                         iDataSize, 2 * 1024 * 1024)) {
                        Log.e(TAG, "openStream failed");
                        return;
                    }
                    while (!is) {
                        线程.暂停(100);
                        Log.i(TAG, "等待0.1s");
                    }
                    if (!Player.getInstance().play(m_iPort,
                                                   播放.getHolder())) {
                        Log.e(TAG, "play failed");
                        return;
                    }
                    if (!Player.getInstance().playSound(m_iPort)) {
                        Log.e(TAG, "playSound failed with error code:"
                              + Player.getInstance().getLastError(m_iPort));
                        return;
                    }
                }
            } else {
                if (!Player.getInstance().inputData(m_iPort, pDataBuffer,
                                                    iDataSize)) {
                    // Log.e(TAG, "inputData failed with: " +
                    // Player.getInstance().getLastError(m_iPort));
                    for (int i = 0; i < 4000 && m_iPlaybackID >= 0
                         && !m_bStopPlayback; i++) {
                        if (Player.getInstance().inputData(m_iPort,
                                                           pDataBuffer, iDataSize)) {
                            break;

                        }

                        if (i % 100 == 0) {
                            Log.e(TAG, "inputData failed with: "
                                  + Player.getInstance()
                                  .getLastError(m_iPort) + ", i:" + i);
                        }

                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();

                        }
                    }
                }

            }
        }

    }

    /**
     * @fn Cleanup
     * @author zhuzhenlei
     * @brief cleanup

     * @return NULL
     */
    public void Cleanup() {
        // release player resource
        Player.getInstance().freePort(m_iPort);
        m_iPort = -1;

        // release net SDK resource
        HCNetSDK.getInstance().NET_DVR_Cleanup();
    }

    @Override
    public void 界面创建事件(Bundle $恢复) {
        try {
        布局_基本界面 布局 = new 布局_基本界面(this);
        布局.打开(this);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        播放 = new SurfaceView(this);
        播放.getHolder().addCallback(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-1, 666);
        播放.setLayoutParams(params);
        布局.标题.置标题("实时预览");
        布局.底层.加入子元素(播放);

        信息 = (监控信息)传入参数[0];
        int $号码 = 监控.登录(信息);
        if ($号码 == -1) {
            提示.警告("连接设备失败");
            结束界面();
            return;
        }
        m_iLogID = 信息.登录码;
        m_iStartChan = 信息.起始;
        m_iChanNum = 信息.通道;
        弹窗 = new 列表弹窗(this);
        弹窗.置标题("选择通道");
        String[] $数组 = new String[信息.通道 - 8];
        for (int $键值 = 0;$键值 < $数组.length;$键值 ++) {
            $数组[$键值] = "" + $键值;
        }
        弹窗.置数组($数组);
        弹窗.置单击(调用.代理(this, "选择"));
        布局.标题.右按钮(图标.搜索, 弹窗.显示);
        
        if (传入参数.length == 2) {
            Integer $通道 = (Integer)传入参数[1];
            m_iStartChan = 信息.起始+ $通道;
            if (startSinglePreview()) {
                提示.普通("正在准备播放 ~");
            } else {
                提示.警告("连接失败 ~");
                跳转界面(ViewActivity.class,信息,$通道);
                结束界面();
            }
            return;
        }
        }catch(Exception $错误) {
           throw new RuntimeException( 错误.取整个错误($错误));
        }
        弹窗.显示();
    }

    private 列表弹窗 弹窗;

    public void 选择(Object[] $参数) {
        弹窗.隐藏();
        int $端口 = new Integer((((布局_适配器_数组)((Object[])($参数[0]))[1])).文本.取文本());
        m_iStartChan = 信息.起始+ $端口;
        stopSinglePreview();
        if (startSinglePreview()) {
            提示.普通("正在准备播放 ~");
        } else {
            提示.警告("连接失败 ~");
            //跳转界面(ViewActivity.class,信息,$端口);
            结束界面();
        }
    }

    @Override
    public void 界面销毁事件() {
        stopSinglePreview();
    }



}
