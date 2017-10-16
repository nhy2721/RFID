package com.botongsoft.rfid.crash;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by pc on 2017/10/16.
 * 在aplication的onCreate中初始化
 * CrashCatchHandler.getInstance().init(getApplicationContext ());
 */

public class CrashCatchHandler implements Thread.UncaughtExceptionHandler {
    public static final String TAG = "CrashCatchHandler";
    //log保存路径
    public static String LOG_PATH;
    private Context mContext;
    //CrashCatchHandler单例
    private static CrashCatchHandler INSTANCE = new CrashCatchHandler();
    //存储设备信息和异常信息
    private Map<String, String> mInfoMap;
    private SimpleDateFormat mDateFormat;

    private CrashCatchHandler() {
        mInfoMap = new LinkedHashMap<>();
        mDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
    }

    /**
     * 获取CrashCatchHandler实例 ,单例模式
     */
    public static CrashCatchHandler getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;
        //设置该CrashCatchHandler为程序的默认未捕获异常处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
        //如果外存卡可以读写，放在外部存储器，否则放在内部存储器上
        //        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
        //            LOG_PATH = mContext.getExternalFilesDir("CARCH_LOG").getPath();
        //        } else {
        //            LOG_PATH = mContext.getFilesDir().getPath();
        LOG_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/com.botongsoft.rfid/crash";
        //        }

    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Toast.makeText(mContext, "很抱歉,程序出现异常,即将退出.", Toast.LENGTH_LONG).show();
        //收集设备参数信息
        collectDeviceInfo(mContext);
        //保存日志文件至本地
        postService(saveCrashLog(ex));
    }

    /**
     * 发送错误日志至服务端
     *
     * @param fileName log路径
     */
    private void postService(String fileName) {
        if (fileName != null) {
            //发送奔溃日志 至服务器
            // TODO: 2016/9/2

        }
    }

    /**
     * 收集奔溃设备参数信息
     */
    public void collectDeviceInfo(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (packageInfo != null) {
                String appName = packageInfo.applicationInfo.packageName;
                String versionName = packageInfo.versionName + "";
                String versionCode = packageInfo.versionCode + "";
                mInfoMap.put("包名", appName);
                mInfoMap.put("版本名", versionName);
                mInfoMap.put("版本号", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "收集设备信息出错", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                mInfoMap.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
                Log.e(TAG, "收集奔溃日志出错", e);
            }
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return 返回文件名称
     */
    private String saveCrashLog(Throwable ex) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : mInfoMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            stringBuilder.append(key + " = " + value + "\n");
        }
        //异常写入stringBuilder
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        //异常原因写入stringBuilder
        Throwable cause = ex.getCause();
        if (cause != null) {
            cause.printStackTrace(printWriter);
        }
        printWriter.close();
        String result = writer.toString();
        stringBuilder.append(result);
        try {
            String time = mDateFormat.format(new Date(System.currentTimeMillis()));
            String fileName = time + ".txt";
            File dir = new File(LOG_PATH);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(dir.getAbsolutePath() + File.separator + fileName);
            fos.write(stringBuilder.toString().getBytes());
            fos.close();
            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "写入文件异常", e);
        }
        return null;
    }
}
