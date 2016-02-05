package com.zzg.robotium.lib;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * UncaughtException处理类,当程序发生Uncaught异常的时候,有该类来接管程序,并记录发送错误报告.
 *
 * @author user
 */
public class CrashHandler implements UncaughtExceptionHandler {

    public static final String TAG = "ROBOT";

    // 系统默认的UncaughtException处理类
    private UncaughtExceptionHandler mDefaultHandler;
    // CrashHandler实例
    private static CrashHandler INSTANCE = new CrashHandler();
    // 程序的Context对象
    public static Context globalContext;
    // 用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<String, String>();

    // 用于格式化日期,作为日志文件名的一部分
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    InputDataStore inputDataStore;
    String filePath;

    /**
     * 保证只有一个CrashHandler实例
     */
    private CrashHandler() {
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        globalContext = context;
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        inputDataStore = InputDataStore.getInstance();
        filePath = inputDataStore.getInput_LogImagePath();
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e(TAG, "error : ", e);
            }
            // 退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        Log.e(TAG, "捕获到无法处理的异常 ex!=null");

        try {
            String shotImage = CommonLib.getCurrentTime() + ".jpg";
            shotScreen(shotImage);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "截图异常" + e.getMessage());
        }
        saveCrashInfo2File(ex);
        // 使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(globalContext, "很抱歉,程序出现异常,即将退出.", Toast.LENGTH_LONG)
                        .show();
                Looper.loop();
            }
        }.start();
        // 保存日志文件
        return true;
    }

    private void shotScreen(String shotImage) {
        ReportLib.setImageName(shotImage);
        Takeshot.shoot((Activity) globalContext,
                filePath + shotImage);
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private String saveCrashInfo2File(Throwable ex) {
        Log.e(TAG, "收集崩溃日志");
        //收集设备信息
        long timestamp = System.currentTimeMillis();
        String time = formatter.format(new Date());
        String fileName = "crash-" + time + "-" + timestamp + ".log";

        JSONObject deviceInfo = getDeviceInfo();
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        try {
            deviceInfo.put("crash_stack", result);
            StackTraceElement[] st = ex.getStackTrace();
            String exclass = st[0].getClassName().toString();
            ReportLib.logWriter(exclass + ":" + DebugInfoStore.Info_Exception, null, deviceInfo, ReportLib.exception);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                String path = inputDataStore.getInput_LogCrash();
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(path + fileName);
                fos.write(deviceInfo.toString().getBytes());
                fos.close();
            } else Log.e(TAG, "没有外部存储器");
            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);
        }
        return null;
    }


    /**
     * 设备及安装软件信息 往jsonGlobal中加入Header信息
     *
     * @return 头信息
     */
    public static JSONObject getDeviceInfo() {
        JSONObject jsonGlobal = new JSONObject();
        try {
            jsonGlobal.put("upload_time", Tools.getCurrentTime4Json());
            jsonGlobal.put("app_version", Tools.getVersionName());// 版本号
            jsonGlobal.put("device_id", !TextUtils.isEmpty(Tools.getIMEI()) ? Tools.getIMEI() : null);// 设备ID
            jsonGlobal.put("device_android_version", Tools.getDeviceVersion());// 设备ID
            jsonGlobal.put("target_android_version", Tools.getTargetAndroidVersion());//目标apk的编译版本
            jsonGlobal.put("device_model", Tools.getPhoneModel());
            jsonGlobal.put("screen_resolution", Tools.getResolution());
            jsonGlobal.put("dpi", Tools.getDpi());
            jsonGlobal.put("wifi_mac", !TextUtils.isEmpty(Tools.getMacAddress()) ? Tools.getMacAddress() : null);
            jsonGlobal.put("network", TextUtils.isEmpty(Tools.getNetworkType()) ? Tools.getNetworkType() : null);// 网络类型
            jsonGlobal.put("use_memory", Tools.getMemory());// 使用的内存
            jsonGlobal.put("totle_memory", Tools.getTotalMemory());// 总内存
            jsonGlobal.put("cpu_used", Tools.getCpuInfo());// CPU使用率
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return jsonGlobal;
        }
    }
}
