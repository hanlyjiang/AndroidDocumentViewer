package com.hanlyjiang.library.utils;

import android.content.Context;
import android.widget.Toast;


/**
 * Android 平台工具函数
 *
 * @author hanlyjiang
 * @version 1.0
 */
public class AndroidUtils {

    /**
     * 以 Toast.LENGTH_SHORT 的时长显示toast，内容为 msg
     *
     * @param context Context
     * @param msg     要显示的内容
     */
    public static void showToast(Context context, String msg) {
        if (context == null) return;
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 以 Toast.LENGTH_SHORT 的时长显示toast，内容为 msg
     *
     * @param context  Context
     * @param msgResId String id
     */
    public static void showToast(Context context, int msgResId) {
        if (context == null) return;
        Toast.makeText(context, context.getResources().getString(msgResId), Toast.LENGTH_SHORT).show();
    }
}
