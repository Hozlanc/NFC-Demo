package com.blake.nfcdemo.utils;

import android.os.Handler;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Create by Pidan
 */
public class ThreadUtils {
    /**
     * 子线程执行task
     */
    public static void runInThread(Runnable task) {
        new Thread(task).start();
    }

    /**
     * 创建一个主线程中handler
     */
    public static Handler mHandler = new Handler();

    /**
     * UI线程执行task
     */
    public static void runInUIThread(Runnable task) {
        mHandler.post(task);
    }

    public static void runDelay(Runnable task, long delayMillis) {
        mHandler.postDelayed(task, delayMillis);
    }

    private static Map<String, Timer> mTimerMap = new HashMap<>();

    public static void period(String key, long period, TimerTask task) {
        if (!mTimerMap.containsKey(key)) {
            Timer timer = new Timer();
            mTimerMap.put(key, timer);
            timer.schedule(task, 0, period);
        }
    }

//    public static void periodLimit(String name, long period, int limit, TimerTask task) {
//        if (mTimerMap.get(name) == null) {
//            Timer timer = new Timer();
//            mTimerMap.put(name, timer);
//            timer.schedule(task, 0, period);
//        }
//    }

//    public static void delay(String key,) {
//        if (!mTimerMap.containsKey(key)) {
//            Timer timer = new Timer();
//            timer.schedule();
//        }
//    }

    public static void cancel(String key) {
        Timer timer = mTimerMap.get(key);
        if (timer != null) {
            mTimerMap.remove(key);
            timer.cancel();
            timer.purge();
        }
    }

    public static long currentTimeMillis() {
//        URL url = new URL("http://www.baidu.com");
//        URLConnection uc = url.openConnection();// 生成连接对象
//        uc.connect(); // 发出连接
//        return uc.getDate();
        return System.currentTimeMillis();
    }
}
