package com.hanlyjiang.library.fileviewer.demo.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * 替代之前封装在MapActivity中的 任务执行函数
 * <br/>
 * Created by hanlyjiang on 2016/12/5.
 */
public class RunnableUtils {

    private static final ExecutorService EXECUTOR = getExecutorService();

    private static ExecutorService getExecutorService() {
        if (EXECUTOR == null) {
            return new ThreadPoolExecutor(2, 2, 60,
                    TimeUnit.SECONDS, new LinkedBlockingDeque<>(), r -> new Thread(r, "RunnableUtils"));
        }
        return EXECUTOR;
    }

    private static UiHandler sUiHandler = new UiHandler(Looper.getMainLooper());

    /**
     * 在UI线程中执行一个Runnable
     *
     * @param task 要执行的任务
     */
    public static void postUi(Runnable task) {
        postUiWork(task);
    }

    /**
     * 在UI线程中执行一个Runnable
     *
     * @param task  要执行的任务
     * @param delay 延迟（MILLISECONDS） 1000ms =  1s
     */
    public static void postUi(Runnable task, int delay) {
        postUiWork(task, delay);
    }

    /**
     * 在后台 线程中执行一个 Callable
     *
     * @param callableTask 要执行的任务
     * @return Future 对象
     */
    public static Future executeOnWorkThread(Callable callableTask) {
        return EXECUTOR.submit(callableTask);
    }

    /**
     * 在IO 线程中执行一个Runnable
     *
     * @param task 要执行的任务
     */
    public static void executeOnWorkThread(Runnable task) {
        EXECUTOR.execute(task);
    }


    private static void postUiWork(Runnable runnable, int delay) {
        Message msg = sUiHandler.obtainMessage();
        msg.obj = runnable;
        msg.arg1 = delay;
        if (sUiHandler == null) {
            sUiHandler = new UiHandler(Looper.getMainLooper());
        }
        sUiHandler.sendMessage(msg);
    }

    private static void postUiWork(Runnable runnable) {
        if (sUiHandler == null) {
            sUiHandler = new UiHandler(Looper.getMainLooper());
        }
        sUiHandler.post(runnable);
    }

    private static class UiHandler extends Handler {

        UiHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.obj != null) {
                if (msg.arg1 > 0) {
                    postDelayed((Runnable) msg.obj, msg.arg1);
                } else {
                    post((Runnable) msg.obj);
                }
            }
        }
    }

}
