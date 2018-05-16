
## 如何使用？
* 参考: [示例](app)

* TBS初始化(在Application中)：
```java
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
```

* **查看文件统一入口：**
```
Uri uri = Uri.fromFile(new File(filePath));
FileViewer.viewFile(context,uri)
```
* **直接使用mupdf查看：**
```java
FileViewer.viewPDFWithMuPDFByPath(Context context, String filePath)
```
或：
```java
FileViewer.startMuPDFActivityByUri(Context context, Uri documentUri)
```

* **直接使用TBS查看word文档：**
```
TBSFileViewActivity.viewFile(context, filePath);
```

## 注意事项
1. office文件无法查看（TBS初始化失败），可以查看这个文档：[无法加载x5内核的解决方案.doc](doc/无法加载x5内核的解决方案.doc)


## 使用到的库：
### PDF查看： mupdf

版本： v1.11.1


> 介绍：
> MuPDF is an open source software framework for viewing and converting PDF, XPS, and E-book documents. There are viewers for various platforms, several command line tools, and a software library for building tools and applications.

项目地址:
https://mupdf.com/docs/

Android 文档:
https://mupdf.com/docs/android-sdk.html

### word等文件查看 ： TBS（腾讯浏览服务）
> **简介:**
> web内核


[官方页面](http://x5.tencent.com/)
