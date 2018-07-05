package com.shout.android.core;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.ActivityCompat;

import com.bridgefy.sdk.client.Bridgefy;
import com.bridgefy.sdk.client.BridgefyClient;
import com.bridgefy.sdk.client.BridgefyUtils;
import com.bridgefy.sdk.client.Device;
import com.bridgefy.sdk.client.Message;
import com.bridgefy.sdk.client.MessageListener;
import com.bridgefy.sdk.client.RegistrationListener;
import com.bridgefy.sdk.client.Session;
import com.bridgefy.sdk.client.StateListener;
import com.bridgefy.sdk.framework.exceptions.MessageException;
import com.shout.android.ChatMessage;

import java.util.ArrayList;
import java.util.UUID;

public class BluetoothClient {

    private ArrayList<ChatMessageListener> chatMessageListeners;
    private ArrayList<ConnectionListener> connectionListeners;
    private int devicesConnected;
    private boolean isStarted = false;
    private static final BluetoothClient INSTANCE = new BluetoothClient();

    public static BluetoothClient getINSTANCE() {
        return INSTANCE;
    }

    private BluetoothClient(){
        chatMessageListeners = new ArrayList<>();
        connectionListeners = new ArrayList<>();
    }

    public void registerChatMessageListener(ChatMessageListener listener){
        chatMessageListeners.add(listener);
    }

    public void unRegisterChatMessageListener(ChatMessageListener listener){
        chatMessageListeners.remove(listener);
    }

    public void registerConnectionListener(ConnectionListener listener){
        connectionListeners.add(listener);
    }

    public void unRegisterConnectionListener(ConnectionListener listener){
        connectionListeners.remove(listener);
    }

    public void initialize(Context c, Activity activity){
        BridgefyUtils.enableBluetooth(c);
        Bridgefy.initialize(c, new RegistrationListener() {
            @Override
            public void onRegistrationSuccessful(BridgefyClient bridgefyClient) {
                startScanning(activity);
            }

            @Override
            public void onRegistrationFailed(int errorCode, String message) {

            }
        });
    }


    public void sendMessage(ChatMessage chatMessage){
        if(isStarted) Bridgefy.sendBroadcastMessage(chatMessage.getMap());
        chatMessageListeners.forEach(listener -> listener.onChatMessageRecieved(chatMessage));
    }



    public void startScanning(Activity activity){
        Bridgefy.start(new MessageListener() {
            @Override
            public void onMessageReceived(Message message) {
                ChatMessage chatMessage = new ChatMessage(message);
                chatMessageListeners.forEach(listener -> listener.onChatMessageRecieved(chatMessage));
            }

            @Override
            public void onMessageSent(Message message) {
                super.onMessageSent(message);
            }

            @Override
            public void onMessageDataProgress(UUID message, long progress, long fullSize) {
                super.onMessageDataProgress(message, progress, fullSize);
            }

            @Override
            public void onMessageReceivedException(String sender, MessageException e) {
                super.onMessageReceivedException(sender, e);
            }

            @Override
            public void onMessageFailed(Message message, MessageException e) {
                super.onMessageFailed(message, e);
            }

            @Override
            public void onBroadcastMessageReceived(Message message) {
                ChatMessage chatMessage = new ChatMessage(message);
                chatMessageListeners.forEach(listener -> listener.onChatMessageRecieved(chatMessage));
            }

        }, new StateListener() {
            @Override
            public void onStarted() {
                isStarted = true;
            }

            @Override
            public void onStartError(String message, int errorCode) {
                if (errorCode == StateListener.INSUFFICIENT_PERMISSIONS) {
                    ActivityCompat.requestPermissions(activity,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                }
            }

            @Override
            public void onStopped() {
                isStarted = false;
            }

            @Override
            public void onDeviceConnected(Device device, Session session) {
                devicesConnected++;
                connectionListeners.forEach(connectionListener -> {
                    connectionListener.deviceConnected();
                    connectionListener.connectedDeviceCountChanged(devicesConnected);
                });
            }

            @Override
            public void onDeviceLost(Device device) {
                devicesConnected--;
                connectionListeners.forEach(connectionListener -> {
                    connectionListener.deviceLost();
                    connectionListener.connectedDeviceCountChanged(devicesConnected);
                });
            }
        });
    }

}
