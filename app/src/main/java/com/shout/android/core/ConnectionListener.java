package com.shout.android.core;

public interface ConnectionListener {

    /**
     * Called when a device is connected
     */
    void deviceConnected(String username, long timestamp, String userID);

    /**
     * Called when a device disconnects or is lost
     */
    void deviceLost(String username, long timestamp, String userID);

    /**
     * @param count how many devices are currently connected
     */
    void connectedDeviceCountChanged(int count);
}
