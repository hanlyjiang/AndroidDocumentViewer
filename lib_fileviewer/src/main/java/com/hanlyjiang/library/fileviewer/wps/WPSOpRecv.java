package com.hanlyjiang.library.fileviewer.wps;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


/**
 * @author hanlyjiang on 2018/3/22-12:09.
 * @version 1.0
 */

public abstract class WPSOpRecv extends BroadcastReceiver {

    private static final String TAG = "WPSOpRecv";

    /**
     * 检测到WPS关闭文件时要启动的Activity
     *
     * @return
     */
    protected abstract Class backActivityClass();

    protected abstract void onReceiveWPSSave(Context context, Intent intent);

    void onReceiveWPSClose(Context context, Intent intent) {
        Intent newintent = new Intent(context, backActivityClass());
        newintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(newintent);
    }

    protected abstract void onReceiveWPSBack(Context context, Intent intent);

    @Override
    public void onReceive(Context context, Intent intent) {
        String stringExtra = intent.getStringExtra(WPSModel.THIRD_PACKAGE);
        boolean isWPSBackToMe = context.getPackageName().contains(stringExtra);
        switch (intent.getAction()) {
            case WPSModel.Reciver.ACTION_BACK://返回键广播
                Log.d(TAG, WPSModel.Reciver.ACTION_BACK);
                if (isWPSBackToMe) {
                    onReceiveWPSBack(context, intent);
                }
                break;
            case WPSModel.Reciver.ACTION_CLOSE://关闭文件时候的广播
                Log.d(TAG, WPSModel.Reciver.ACTION_CLOSE + "/" + isWPSBackToMe);
                if (isWPSBackToMe) {
                    onReceiveWPSClose(context, intent);
                }
                break;
            case WPSModel.Reciver.ACTION_HOME://home键广播
                Log.d(TAG, WPSModel.Reciver.ACTION_HOME);
                break;
            case WPSModel.Reciver.ACTION_SAVE://保存广播
                Log.d(TAG, WPSModel.Reciver.ACTION_SAVE);
                if (isWPSBackToMe) {
                    onReceiveWPSSave(context, intent);
                }
                break;
            default:
                break;
        }

    }

    /*
    需要注册如下 BroadcastReceiver

    <receiver android:name="{WPSOpRecv 的 BroadcastReceiver子类}">
            <intent-filter>
                <action android:name="com.kingsoft.writer.back.key.down" />
                <action android:name="cn.wps.moffice.file.close" />
                <action android:name="com.kingsoft.writer.back.key.down" />
                <action android:name="com.kingsoft.writer.home.key.down" />
            </intent-filter>
     </receiver>

     */
}

