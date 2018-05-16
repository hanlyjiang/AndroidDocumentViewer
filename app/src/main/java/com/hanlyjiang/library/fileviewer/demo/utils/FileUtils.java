package com.hanlyjiang.library.fileviewer.demo.utils;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class FileUtils {

    /**
     * 拷贝Asset文件夹到sd卡
     *
     * @param context
     * @param fromDir 如果拷贝assets ，则传入 ""
     * @param destDir 目的地
     * @throws IOException
     */
    public static void copyAssetsDir(Context context, String fromDir, String destDir) throws IOException {
        String[] files = context.getAssets().list(fromDir);
        for (String f : files) {
            copyFile(context.getAssets().open(fromDir + File.separator + f), destDir + File.separator + f);
        }
    }

    public static void copyFile(InputStream in, String newPath) {
        try (
                InputStream inStream = in;
                FileOutputStream fs = new FileOutputStream(newPath)
        ) {
            int byteread;
            new File(newPath).getParentFile().mkdirs();
            byte[] buffer = new byte[4096];
            while ((byteread = inStream.read(buffer)) != -1) {
                fs.write(buffer, 0, byteread);
                fs.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
