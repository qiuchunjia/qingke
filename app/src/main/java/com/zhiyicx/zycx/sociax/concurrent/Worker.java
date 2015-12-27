package com.zhiyicx.zycx.sociax.concurrent;

import android.os.Looper;

import com.zhiyicx.zycx.sociax.android.Thinksns;

public class Worker implements Runnable {
    private final Object mLock = new Object();
    private Looper mLooper;
    private Thinksns app;
    private Thread thread;
    private static final int SLEEP_TIME = 3000;

    public Worker(Thinksns app) {
        this.init(app, "Default Worker");
    }

    public Worker(Thinksns app, String name) {
        this.init(app, name);
    }

    private void init(Thinksns app, String name) {
        thread = new Thread(null, this, name);
        this.setApp(app);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();

        synchronized (mLock) {
            while (mLooper == null) {
                try {
                    mLock.wait();
                } catch (InterruptedException ex) {
                }
            }
        }
    }

    public Looper getLooper() {
        return mLooper;
    }

    @Override
    public void run() {
        synchronized (mLock) {
            Looper.prepare();
            mLooper = Looper.myLooper();
            mLock.notifyAll();

        }
        Looper.loop();
    }

    public void sleep() {
        this.sleep(SLEEP_TIME);
    }

    public void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {

        }
    }

    public void quit() {
        mLooper.quit();
    }

    public Thinksns getApp() {
        return app;
    }

    public void setApp(Thinksns app) {
        this.app = app;
    }
}
