package com.shout.android.core;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

class ForegroundBackgroundListener implements LifecycleObserver {

    private boolean state = false;

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void setForeground() {
        state = true;
    }

    public boolean isBackground() {
        return !state;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void setBackground() {
        state = false;
    }
}
