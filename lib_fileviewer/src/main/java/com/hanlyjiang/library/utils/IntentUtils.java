package com.hanlyjiang.library.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.artifex.mupdf.R;

import java.io.File;

/**
 * @author hanlyjiang on 2018/5/16-16:53.
 * @version 1.0
 */

class IntentUtils {

    static final String IS_DOCUMENT = "isDoc";

    /**
     * 通过文件后缀名来获取对应的Intent
     *
     * @param context  Context 对象
     * @param fileName 文件名称
     * @return 对应的Intent 或 null - 不支持的文件类型
     */
    public static Intent getFileViewIntent(Context context, String fileName) {
        String ext = FileViewerUtils.getExtension(fileName);
        boolean isDocument = false;
        if (TextUtils.isEmpty(ext)) {
            AndroidUtils.showToast(context, context.getString(R.string.file_not_recognized));
            return null;
        }
        Intent intent = null;
        if (".jpg".equalsIgnoreCase(ext) || ".jpeg".equalsIgnoreCase(ext) || ".png".equalsIgnoreCase(ext) || ".gif".equalsIgnoreCase(ext)) {
            intent = getImageFileIntent(fileName);
        } else if (".xls".equalsIgnoreCase(ext) || ".xlsx".equalsIgnoreCase(ext) || ".et".equalsIgnoreCase(ext) || ".ett".equalsIgnoreCase(ext)) {
            intent = getExcelFileIntent(fileName);
            isDocument = true;
        } else if (".doc".equalsIgnoreCase(ext) || ".docx".equalsIgnoreCase(ext) || ".wps".equalsIgnoreCase(ext) || ".wpt".equalsIgnoreCase(ext)) {
            intent = getWordFileIntent(fileName);
            isDocument = true;
        } else if (".ppt".equalsIgnoreCase(ext) || ".pptx".equalsIgnoreCase(ext) || ".dps".equalsIgnoreCase(ext) || ".dpt".equalsIgnoreCase(ext)) {
            intent = getPptFileIntent(fileName);
            isDocument = true;
        } else if (".pdf".equalsIgnoreCase(ext)) {
            intent = getPdfFileIntent(fileName);
            isDocument = true;
        } else if (".htm".equalsIgnoreCase(ext) || ".html".equalsIgnoreCase(ext) || ".jsp".equalsIgnoreCase(ext) || ".css".equalsIgnoreCase(ext)) {
            intent = getHtmlFileIntent(fileName);
        } else if (".txt".equalsIgnoreCase(ext) || ".text".equalsIgnoreCase(ext)) {
            intent = getHtmlFileIntent(fileName);
        } else if (".mp3".equalsIgnoreCase(ext) || ".wma".equalsIgnoreCase(ext) || ".aar".equalsIgnoreCase(ext) || ".m4a".equalsIgnoreCase(ext)) {
            intent = getAudioFileIntent(fileName);
        } else if (".mp4".equalsIgnoreCase(ext) || ".avi".equalsIgnoreCase(ext) || ".flv".equalsIgnoreCase(ext)) {
            intent = getVideoFileIntent(fileName);
        } else {

        }
        if (intent != null) {
            intent.putExtra(IS_DOCUMENT, isDocument);
        }
        return intent;
    }

    static Intent getExcelFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        return intent;
    }

    static Intent getHtmlFileIntent(String param) {
        Uri uri = Uri.parse(param).buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme("content").encodedPath(param).build();
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(uri, "text/html");
        return intent;
    }


    static Intent getImageFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "image/*");
        return intent;
    }


    static Intent getPdfFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/pdf");
        return intent;
    }


    static Intent getTextFileIntent(String paramString, boolean paramBoolean) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (paramBoolean) {
            Uri uri1 = Uri.parse(paramString);
            intent.setDataAndType(uri1, "text/plain");
        }
        return intent;
    }


    static Intent getAudioFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "audio/*");
        return intent;
    }


    static Intent getVideoFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "video/*");
        return intent;
    }

    static Intent getChmFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/x-chm");
        return intent;
    }

    static Intent getWordFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }

    static Intent getPptFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        return intent;
    }

    static Intent getApkFileIntent(String fileName) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(fileName));
        intent.setDataAndType(uri, "application/vnd.android");
        return intent;
    }
}
