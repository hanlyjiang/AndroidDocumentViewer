package com.hanlyjiang.library.fileviewer.wps;

/**
 * @author hanlyjiang on 2018/3/22-12:04.
 * @version 1.0
 */

class WPSModel {
    public static final String OPEN_MODE = "OpenMode";// 打开文件的模式。
    public static final String SEND_SAVE_BROAD = "SendSaveBroad";// 文件保存时是否发送广播。
    public static final String SEND_CLOSE_BROAD = "SendCloseBroad";// 文件关闭时是否发送广播
    public static final String THIRD_PACKAGE = "ThirdPackage";// 第三方的包名，关闭的广播会包含该项。
    public static final String CLEAR_BUFFER = "ClearBuffer";// 关闭文件时是否请空临时文件。
    public static final String CLEAR_TRACE = "ClearTrace";// 关闭文件时是否删除使用记录。
    public static final String CLEAR_FILE = "ClearFile";// 关闭文件时是否删除打开的文件。
    public static final String VIEW_PROGRESS = "ViewProgress";// 文件上次查看的进度。
    public static final String AUTO_JUMP = "AutoJump";// 是否自动跳转到上次查看的进度。
    public static final String SAVE_PATH = "SavePath";// 文件保存路径。
    public static final String VIEW_SCALE = "ViewScale";// 文件上次查看的视图的缩放。
    public static final String VIEW_SCALE_X = "ViewScrollX";// 文件上次查看的视图的X坐标。
    public static final String VIEW_SCALE_Y = "ViewScrollY";// 文件上次查看的视图的Y坐标。
    public static final String USER_NAME = "UserName";// 批注的作者。
    public static final String HOMEKEY_DOWN = "HomeKeyDown";// 监听home键并发广播
    public static final String BACKKEY_DOWN = "BackKeyDown";// 监听back键并发广播
    public static final String ENTER_REVISE_MODE = "EnterReviseMode";// 以修订模式打开文档
    public static final String CACHE_FILE_INVISIBLE = "CacheFileInvisible";// Wps生成的缓存文件外部是否可见

    public class OpenMode {
        public static final String NORMAL = "Normal";// 只读模式
        public static final String READ_ONLY = "ReadOnly";// 正常模式
        public static final String READ_MODE = "ReadMode";// 打开直接进入阅读器模式
        // 仅Word、TXT文档支持
        public static final String SAVE_ONLY = "SaveOnly";// 保存模式(打开文件,另存,关闭)
        // 仅Word、TXT文档支持
    }

    public class ClassName {
        public static final String NORMAL = "cn.wps.moffice.documentmanager.PreStartActivity2";// 普通版
        public static final String ENGLISH = "cn.wps.moffice.documentmanager.PreStartActivity2";// 英文版
        public static final String ENTERPRISE = "cn.wps.moffice.documentmanager.PreStartActivity2";// 企业版
    }

    public class PackageName {
        public static final String NORMAL = "cn.wps.moffice_eng";// 普通版
        public static final String ENGLISH = "cn.wps.moffice_eng";// 英文版
    }

    public class Reciver {
        public static final String ACTION_BACK = "com.kingsoft.writer.back.key.down";// 返回键广播
        public static final String ACTION_HOME = "com.kingsoft.writer.home.key.down";// Home键广播
        public static final String ACTION_SAVE = "cn.wps.moffice.file.save";// 保存广播
        public static final String ACTION_CLOSE = "cn.wps.moffice.file.close";// 关闭文件广播
    }

}
