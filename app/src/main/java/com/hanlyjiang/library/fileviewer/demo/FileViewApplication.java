package com.hanlyjiang.library.fileviewer.demo;

import android.app.Application;
import android.util.Log;

import com.hanlyjiang.library.fileviewer.demo.utils.FileUtils;
import com.tencent.smtt.sdk.QbSdk;

import java.io.IOException;

/**
 *
 */
public class FileViewApplication extends Application {

    public static final String FILE_DIR = "/sdcard/Downloads/test/";
    public static final String TAG = "TBSInit";

    @Override
    public void onCreate() {
        super.onCreate();
        initX5Web();

        try {
            FileUtils.copyAssetsDir(this, "test", FILE_DIR);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void initX5Web() {
        Log.i(TAG, "QbSdk.initX5Environment");
        QbSdk.initX5Environment(getApplicationContext(), new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                Log.d(TAG, "onCoreInitFinished");
            }

            @Override
            public void onViewInitFinished(boolean initResult) {
                Log.e(TAG, "onViewInitFinished" + initResult);
            }
        });
    }


}
