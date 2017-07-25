//package com.botongsoft.rfid.common.service.http;
//
//import android.content.SharedPreferences;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.os.Environment;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ProgressBar;
//import android.widget.Toast;
//
//import com.botongsoft.rfid.R;
//import com.botongsoft.rfid.ui.activity.MainActivity;
//
//import org.apache.http.HttpStatus;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.RandomAccessFile;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
///**
// * Created by pc on 2017/7/25.
// */
//
//public class testextends Activity implements OnClickListener {
//    private Button btnStart;
//    private Button btnStop;
//    private ProgressBar pb;
//    private int length;
//    private int start;
//    private boolean isPause;
//    private SharedPreferences mSharedPreferences;
//    private Editor mEditor;
//    public int mFinished;
//    private boolean flag;
//    // 下载路径
//    private final static String MYURL = "http://www.imooc.com/mobile/mukewang.apk";
//    // 下载地址
//    private final static String DIRSTR = Environment
//            .getExternalStorageDirectory().getAbsolutePath() + "/";
//    // 下载文件名
//    private final static String FILENAME = "mukewang.apk";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.mian_layout);
//        init();
//    }
//
//    /*
//     * 重写返回键,模拟返回按下暂停键
//     */
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        switch (keyCode) {
//            case KeyEvent.KEYCODE_BACK:
//                btnStop.performClick();// 模拟按下暂停键
//                break;
//            default:
//                break;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//    /*
//     * 初始化控件
//     */
//    private void init() {
//        btnStart = (Button) findViewById(R.id.button1);
//        btnStop = (Button) findViewById(R.id.button2);
//        pb = (ProgressBar) findViewById(R.id.progressBar1);
//        pb.setMax(100);
//        btnStart.setOnClickListener(this);
//        btnStop.setOnClickListener(this);
//    }
//
//    /*
//     * 异步处理
//     */
//    class MyAsyncTask extends AsyncTask<String, Integer, String> {
//
//        @Override
//        protected String doInBackground(String... params) {
//            // 获取文件长度
//            getFileLength(params[0]);
//            // 执行下载任务
//            return download(params[0]);
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            Log.d("result: ", result);
//            // 返回值设置Toast
//            Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT)
//                    .show();
//        }
//
//        @Override
//        protected void onProgressUpdate(Integer... values) {
//            super.onProgressUpdate(values);
//            // 更新进度条
//            pb.setProgress(values[0]);
//        }
//
//        /*
//         * 获取文件长度
//         */
//    private void getFileLength(String string) {
//        HttpURLConnection connection = null;
//        try {
//            connection = (HttpURLConnection) new URL(string)
//                    .openConnection();
//            connection.setRequestMethod("GET");
//            connection.setConnectTimeout(3000);
//            if (connection.getResponseCode() != HttpStatus.SC_OK) {
//                return;
//            }
//            length = connection.getContentLength();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (connection != null) {
//                connection.disconnect();
//            }
//        }
//
//    }
//
//    /*
//     * 下载任务
//     */
//    private String download(String string) {
//        HttpURLConnection connection = null;
//        InputStream in = null;
//        RandomAccessFile raf = null;
//        try {
//            // 从SharedPreferences取出DownloadInfo里的下载进度值
//            mSharedPreferences = getSharedPreferences("DownloadInfo",
//                    MODE_PRIVATE);
//            mEditor = mSharedPreferences.edit();
//            start = mSharedPreferences.getInt("Finished", 0);
//            mFinished = start;
//            connection = (HttpURLConnection) new URL(string)
//                    .openConnection();
//            connection.setRequestMethod("GET");
//            connection.setConnectTimeout(3000);
//            Log.d("length: ", length + "");
//            connection.setRequestProperty("Range", "bytes=" + start + "-"
//                    + length);
//            if (connection.getResponseCode() != HttpStatus.SC_PARTIAL_CONTENT) {
//                return "206";
//            }
//            in = connection.getInputStream();
//            File file = new File(DIRSTR, FILENAME);
//            raf = new RandomAccessFile(file, "rwd");
//            Log.d("start:", start + "");
//            raf.seek(start);
//            byte[] buffer = new byte[1024 * 4];
//            int len;
//            while ((len = in.read(buffer)) != -1) {
//                raf.write(buffer, 0, len);
//                mFinished += len;
//                // 设置进度条的值
//                publishProgress(mFinished * 100 / length);
//                // 判断是否暂停
//                if (isPause) {
//                    Log.d("mFinished:", mFinished + "");
//                    // 将进度值存入SharedPreferences中
//                    mEditor.putInt("Finished", mFinished);
//                    mEditor.commit();
//                    if (raf != null) {
//                        raf.close();
//                    }
//                    return "暂停";
//                }
//            }
//            flag = false;
//            // 下载完成,从SharedPreferences移除下载进度
//            mEditor.remove("Finished");
//            mEditor.commit();
//            return "下载完成!";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return e.getMessage();
//        } finally {
//            try {
//                if (raf != null) {
//                    raf.close();
//                }
//                if (in != null) {
//                    in.close();
//                }
//                if (connection != null) {
//                    connection.disconnect();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//                return e.getMessage();
//            }
//        }
//    }
//
//}
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.button1:
//                if (flag) {
//                    return;
//                }
//                isPause = false;
//                // 防止多次点击下载,造成多个下载
//                flag = true;
//                Log.d("onClick", "开始");
//                // 执行下载任务
//                new MyAsyncTask().execute(MYURL);
//                break;
//            case R.id.button2:
//                // 通过isPause暂停下载
//               flag
//                break;
//            default:
//                break;
//        }
//    }
//}
