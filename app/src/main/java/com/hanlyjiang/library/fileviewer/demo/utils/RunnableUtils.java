package com.hanlyjiang.library.fileviewer.demo.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * 替代之前封装在MapActivity中的 任务执行函数
 * <br/>
 * Created by hanlyjiang on 2016/12/5.
 */
public class RunnableUtils {

    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(6);
    private static UIHandler sUIHandler = new UIHandler(Looper.getMainLooper());

    /**
     * 在UI线程中执行一个Runnable
     *
     * @param task 要执行的任务
     */
    public static void postUI(Runnable task) {
//        AndroidSchedulers.mainThread().scheduleDirect(task);
        postUIWork(task);  // 不使用RXJava 的实现
    }

    /**
     * 在UI线程中执行一个Runnable
     *
     * @param task  要执行的任务
     * @param delay 延迟（MILLISECONDS） 1000ms =  1s
     */
    public static void postUI(Runnable task, int delay) {
//        AndroidSchedulers.mainThread().scheduleDirect(task,delay, TimeUnit.MILLISECONDS);
        postUIWork(task, delay);    //不使用RXJava 的实现
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
//        Schedulers.from(EXECUTOR).scheduleDirect(task);
        EXECUTOR.execute(task);
    }

    private static void postUIWork(Runnable runnable, int delay) {
        Message msg = sUIHandler.obtainMessage();
        msg.obj = runnable;
        msg.arg1 = delay;
        if (sUIHandler == null) {
            sUIHandler = new UIHandler(Looper.getMainLooper());
        }
        sUIHandler.sendMessage(msg);
    }

    private static void postUIWork(Runnable runnable) {
        if (sUIHandler == null) {
            sUIHandler = new UIHandler(Looper.getMainLooper());
        }
        sUIHandler.post(runnable);
    }

    private static class UIHandler extends Handler {

        UIHandler(Looper looper) {
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
