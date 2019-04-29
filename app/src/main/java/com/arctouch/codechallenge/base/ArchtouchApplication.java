package com.arctouch.codechallenge.base;

import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.arctouch.codechallenge.di.ApplicationDependency;
import com.arctouch.codechallenge.di.components.ApplicationComponent;
import com.arctouch.codechallenge.di.components.ComponentHolder;

public class ArchtouchApplication extends MultiDexApplication {

    private static Context context = null;
    public static String TAG = ArchtouchApplication.class.getSimpleName();
    public static ApplicationComponent graph;

    private static ArchtouchApplication instance = null;

    public static ArchtouchApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.initializeInjector();
        ComponentHolder holder = ComponentHolder.INSTANCE;
        graph.inject(holder);

        this.TAG = this.getClass().getSimpleName();
        instance = this;
        ArchtouchApplication.context = getApplicationContext();
    }

    private void initializeInjector() {
        graph = new ApplicationDependency().getApplicationComponent(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return graph;
    }

    public static Context getAppContext() {
        return ArchtouchApplication.context;
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

        switch (level) {
            case TRIM_MEMORY_COMPLETE:
                this.tryToCleanMemory();
                break;

            case TRIM_MEMORY_UI_HIDDEN:
                this.tryToCleanMemory();
                break;

            case TRIM_MEMORY_RUNNING_CRITICAL:
                this.cleanMemoryCache();
                break;
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.d(TAG, "onLowMemory");
        this.cleanMemoryCache();
    }

    private void tryToCleanMemory() {
        this.cleanMemoryCache();
        System.gc();
    }

    private void cleanMemoryCache() {
        //todo: limpar dados
    }

}