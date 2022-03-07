package com.hanlyjiang.library.fileviewer.wps;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.util.Log;

import com.hanlyjiang.library.utils.AndroidUtils;
import com.hanlyjiang.library.utils.FileViewerUtils;

import java.io.File;
import java.util.List;


/**
 * WPS打开文件工具
 *
 * @author hanlyjiang on 2018/3/22-13:49.
 * @version 1.0
 */

public class WPSOpenUtils {


    /**
     * 用WPS 打开应用,注意此种方法无法获取
     *
     * @param filePath 设置好数据了类型的intent
     * @param context  Context 对象
     * @return
     */
    public static boolean viewOrEditFileWithWPS(@NonNull Context context, String filePath, boolean readOnly) {
        return viewOrEditFileWithWPS(new ContextStartWrapper(context), filePath, readOnly, 0);
    }

    /**
     * 用WPS 打开文档
     *
     * @param fragment
     * @param filePath    设置好数据了类型的intent
     * @param readOnly    true - 以只读模式打开文档通过StartActivity启动）； false - 以编辑模式打开文档（通过StartActivityForResult启动）
     * @param requestCode 指定通过StartActivityForResult启动时的requestCode
     * @return
     */
    public static boolean viewOrEditFileWithWPS(@NonNull Fragment fragment, String filePath, boolean readOnly, int requestCode) {
        return viewOrEditFileWithWPS(new FragmentStartWrapper(fragment), filePath, readOnly, requestCode);
    }

    /**
     * 用WPS 打开应用
     *
     * @param filePath 设置好数据了类型的intent
     * @param wrapper  ActivityStarterWrapper 对象
     * @return
     */
    protected static boolean viewOrEditFileWithWPS(@NonNull ActivityStarterWrapper wrapper, String filePath, boolean readOnly, int requestCode) {
        Intent intent = new Intent();
        File file = new File(filePath);
        if (!file.isFile()) {
            AndroidUtils.showToast(wrapper.getContext(), "文件不存在");
            return false;
        }
        intent.setDataAndType(Uri.fromFile(file), FileViewerUtils.getMimeType(file));
        return doRealWPSViewOrEdit(wrapper, intent, readOnly, requestCode);
    }


    /**
     * 用WPS 打开应用
     *
     * @param context Context 对象
     * @param intent  设置好数据了类型的intent
     * @return
     */
    protected static boolean doRealWPSViewOrEdit(@NonNull ActivityStarterWrapper context, @NonNull Intent intent,
                                                 boolean readOnly, int requestCode) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(WPSModel.SEND_CLOSE_BROAD, true); // 关闭时是否发送广播
        bundle.putBoolean(WPSModel.SEND_SAVE_BROAD, true); // 保存时是否发送广播
        bundle.putString(WPSModel.THIRD_PACKAGE, context.getPackageName()); // 第三方应用的包名，用于对改应用合法性的验证
        if (readOnly) {
            bundle.putString(WPSModel.OPEN_MODE, WPSModel.OpenMode.READ_ONLY); // 打开模式
        } else {
            bundle.putString(WPSModel.OPEN_MODE, WPSModel.OpenMode.NORMAL); // 打开模式
            bundle.putBoolean(WPSModel.ENTER_REVISE_MODE, true); // 修订模式
        }
        bundle.putBoolean(WPSModel.CLEAR_TRACE, true);// 清除打开记录
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setClassName(WPSModel.PackageName.NORMAL, WPSModel.ClassName.NORMAL);
        intent.putExtras(bundle);
        try {
            if (readOnly) {
                context.startActivity(Intent.createChooser(intent, "请选择WPS打开文件"));
            } else {
                context.startActivityForResult(Intent.createChooser(intent, "请选择WPS打开应用"), requestCode);
            }
        } catch (ActivityNotFoundException exception) {
            Log.d("WPSOpenUtils", exception.getMessage());
            exception.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * 检测是否有安装 WPS
     *
     * @param context Context
     * @return
     */
    public static boolean checkWPSInstallation(Context context) {
        PackageManager packageManager1 = context.getPackageManager();
        Intent intent = new Intent();
        intent.setClassName(WPSModel.PackageName.NORMAL, WPSModel.ClassName.NORMAL);
        List<ResolveInfo> resolveInfos = packageManager1.queryIntentActivities(intent, PackageManager.MATCH_ALL);
        if (resolveInfos == null || resolveInfos.size() == 0) {
            return false;
        }
        boolean found = false;
        for (ResolveInfo info : resolveInfos) {
            if (info.activityInfo.packageName.toLowerCase().equalsIgnoreCase(WPSModel.PackageName.NORMAL)
                    || info.activityInfo.name.toLowerCase().equalsIgnoreCase(WPSModel.ClassName.NORMAL)) {
                found = true;
                break;
            }
        }
        return found;
    }

    abstract static class ActivityStarterWrapper {

        protected Context context;

        public ActivityStarterWrapper(Context context) {
            this.context = context;
        }

        abstract String getPackageName();

        abstract void startActivityForResult(Intent intent, int requestCode);

        abstract void startActivity(Intent intent);

        public Context getContext() {
            return context;
        }
    }

    protected static class ContextStartWrapper extends ActivityStarterWrapper {

        public ContextStartWrapper(Context context) {
            super(context);
        }

        @Override
        String getPackageName() {
            return context.getPackageName();
        }

        @Override
        void startActivityForResult(Intent intent, int requestCode) {
            context.startActivity(intent);
        }

        @Override
        void startActivity(Intent intent) {
            context.startActivity(intent);
        }
    }

    protected static class FragmentStartWrapper extends ActivityStarterWrapper {

        Fragment fragment;

        public FragmentStartWrapper(Fragment fragment) {
            super(fragment.getContext());
            this.fragment = fragment;
        }

        @Override
        String getPackageName() {
            return (fragment.getContext() == null) ? "" : fragment.getContext().getPackageName();
        }

        @Override
        void startActivityForResult(Intent intent, int requestCode) {
            fragment.startActivityForResult(intent, requestCode);
        }

        @Override
        void startActivity(Intent intent) {
            fragment.startActivity(intent);
        }
    }
}
