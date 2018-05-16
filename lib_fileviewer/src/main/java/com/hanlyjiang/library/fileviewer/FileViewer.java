package com.hanlyjiang.library.fileviewer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.artifex.mupdf.viewer.DocumentActivity;
import com.hanlyjiang.library.fileviewer.tbs.TBSFileViewActivity;
import com.hanlyjiang.library.utils.FileViewerUtils;

import java.io.File;


/**
 * 文件查看入口
 *
 * @author hanlyjiang on 2017/12/13-16:59.
 * @version 1.0
 */

public class FileViewer {

    private static final String DOT = ".";
    private static final String FORMAT_UNKNOWN = "unknown";
    private static final String FORMAT_PDF = "pdf";
    private static final String TAG = "FileViewUtils";
    private static final String[] FORMAT_TBS = new String[]{
            "doc", "docx",
            "ppt", "pptx",
            "xls", "xlsx",
            "txt",
            "pdf",
            "epub",
    };
    private static final String[] FORMAT_IMAGE = new String[]{
            "png", "jpg",
            "jpeg", "gif",
            "bmp",
    };

    /**
     * 默认的文件查看入口,仅支持下载过的文件
     *
     * @param context
     * @param filePath
     */
    public static OpenResult viewFile(Context context, String filePath) {
        if (context == null) {
            log("Context 为null ViewFile!  直接返回");
            return OpenResult.failed("Context 为 null");
        }

        if (TextUtils.isEmpty(filePath)) {
            return OpenResult.failed("FilePath:" + filePath + " is Empty!");
        }

        File file = new File(filePath);
        if (!file.isFile()) {
            return OpenResult.failed("文件不存在 " + filePath + " 不存在");
        }

        String format = parseFormat(filePath);
        if (FORMAT_UNKNOWN.equals(format)) {
            return OpenResult.failed("不支持的文件格式：" + format);
        }

        if (isPDF(format)) {
            viewPDFWithMuPDFByPath(context, filePath);
            log("PDF 格式文件，使用muPDF浏览");
            return OpenResult.success();
        }

        if (isTBSSupportFile(format)) {
            TBSFileViewActivity.viewFile(context, filePath);
            log(format + " ： TBS默认支持文件类型，使用TBS浏览");
            return OpenResult.success();
        }

        FileViewerUtils.viewFile4_4(context, filePath);
        return OpenResult.success();
    }

    private static void log(String msg) {
        Log.e(TAG, msg);
    }

    public static void viewPDFWithMuPDFByPath(Context context, String filePath) {
        Uri uri = Uri.fromFile(new File(filePath));
        startMuPDFActivityByUri(context, uri);
    }

    public static void startMuPDFActivityByUri(Context context, Uri documentUri) {
        Intent intent = new Intent(context, DocumentActivity.class);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(documentUri);
        context.startActivity(intent);
    }

    /**
     * 判断是否为图片文件
     *
     * @param format 文件后缀
     * @return 图片文件 - true
     */
    private static boolean isImageFile(String format) {
        for (String imageFormat : FORMAT_IMAGE) {
            if (imageFormat.equalsIgnoreCase(format)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是 TBS 文件浏览服务支持的文件格式
     *
     * @param format
     * @return
     */
    private static boolean isTBSSupportFile(String format) {
        for (String tbsFormat : FORMAT_TBS) {
            if (tbsFormat.equalsIgnoreCase(format)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isPDF(String format) {
        if (FORMAT_PDF.equalsIgnoreCase(format)) {
            return true;
        }
        return false;
    }

    /**
     * 解析文件后缀名，不带 "."
     *
     * @param fileName 文件名（可以为全路径）
     * @return 文件后缀名，不带 "."
     */
    private static String parseFormat(String fileName) {
        if (!fileName.contains(DOT)) {
            return FORMAT_UNKNOWN;
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public static class OpenResult {
        boolean isSuccess = true;
        String failedResult = "";

        OpenResult(boolean isSuccess, String failedResult) {
            this.isSuccess = isSuccess;
            this.failedResult = failedResult;
        }

        static OpenResult success() {
            return new OpenResult(true, "");
        }

        static OpenResult failed(String result) {
            return new OpenResult(false, result);
        }

        public boolean isSuccess() {
            return isSuccess;
        }

        public String getFailedResult() {
            return failedResult;
        }
    }

}
