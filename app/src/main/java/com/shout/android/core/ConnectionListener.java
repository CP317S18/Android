package com.shout.android.core;

public interface ConnectionListener {

    /**
     * Called when a device is connected
     */
    void deviceConnected();

    /**
     * Called when a device disconnects or is lost
     */
    void deviceLost();

    /**
     * @param count how many devices are currently connected
     */
    void connectedDeviceCountChanged(int count);
}
