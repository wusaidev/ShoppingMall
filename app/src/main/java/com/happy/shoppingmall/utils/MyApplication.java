package com.happy.shoppingmall.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import org.xutils.BuildConfig;
import org.xutils.x;

/**
 * Created by happy on 2017/4/29.
 */

public class MyApplication extends Application {
    private static Context context;
        @Override
        public void onCreate() {
            super.onCreate();
            context=getApplicationContext();
			getException();
            initXUtil();
        }

	private void initXUtil() {
		x.Ext.init(this);
		x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
	}

	private void getException() {
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread thread, Throwable ex) {
				ex.printStackTrace();
				Log.e("MyApplication","捕获到了一个异常");

				String path=Environment.getExternalStorageDirectory().getAbsoluteFile()+File.separator+"msError.log";
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
