package com.happy.shoppingmall.utils;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.zhy.http.okhttp.OkHttpUtils;

import org.xutils.BuildConfig;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * 作者：wusai
 * QQ:2713183194
 * 作用：xxx
 * Created by happy on 2017/6/11.
 */

public class ShoppingMallApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        initOkHttpUtils();
        initXUtil();
        getException();
    }

    /**
     * OkHttpUtils初始化方法
     */
    private void initOkHttpUtils() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }

    private void initXUtil() {
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
    }

    private void getException() {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                ex.printStackTrace();
                Log.e("MyApplication","捕获到了一个异常");

                String path= Environment.getExternalStorageDirectory().getAbsoluteFile()+ File.separator+"msError.log";
                File file=new File(path);
                PrintWriter writer;
                try {
                    writer = new PrintWriter(file);
                    ex.printStackTrace(writer);
                    writer.close();
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                //将文件发送到公司服务器
                //退出
                System.exit(0);
            }
        });
    }

    public static Context getContext(){
        return context;
    }

}
