package com.hanlyjiang.library.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import com.artifex.mupdf.R;

import java.io.File;

/**
 * 文件查看相关工具类
 *
 * @author hanlyjiang 2017/3/13
 * @version 1.0
 */
public class FileViewerUtils {

    public static final int FILE_EDIT = 0;

    /**
     * 查看文件
     *
     * @param filePath
     */
    public static void viewFile(Context activity, String filePath) {
        File viewFile = new File(filePath);
        Intent intent = new Intent();
        intent.setDataAndType(Uri.fromFile(viewFile), getMimeType(viewFile));
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    /**
     * Android 4.4 系统上文件查看
     *
     * @param context
     * @param fileName
     */
    public static void viewFile4_4(Context context, String fileName) {
        String ext = getExtension(fileName);
        if (TextUtils.isEmpty(ext)) {
            AndroidUtils.showToast(context, context.getString(R.string.file_not_recognized));
            return;
        }
        Intent intent = IntentUtils.getFileViewIntent(context, fileName);
        if (intent != null) {
            Intent newIntent = Intent.createChooser(intent, context.getString(R.string.choose_one_activity_to_open));
            context.startActivity(newIntent);
        } else {
            FileViewerUtils.viewFile(context, fileName);
        }
    }

    /**
     * Gets the extension of a file name, like ".png" or ".jpg".
     *
     * @param fileName
     * @return Extension including the dot("."); "" if there is no extension;
     * null if fileName was null.
     */
    public static String getExtension(String fileName) {
        if (fileName == null) {
            return null;
        }

        int dot = fileName.lastIndexOf(".");
        if (dot >= 0) {
            return fileName.substring(dot);
        } else {
            // No extension.
            return "";
        }
    }


    /**
     * Get The MIME type for the given file.
     *
     * @param file given file
     * @return The MIME type for the given file.
     */
    public static String getMimeType(File file) {

        String extension = getExtension(file.getName());

        if (extension.length() > 0)
            return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.substring(1));

        return "application/octet-stream";
    }
}
