package com.shout.android.core;

public interface ConnectionListener {

    void deviceConnected();
    void deviceLost();
    void connectedDeviceCountChanged(int count);
}
