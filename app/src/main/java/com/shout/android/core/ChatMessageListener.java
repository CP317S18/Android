package com.shout.android.core;

import com.shout.android.ChatMessage;

public interface ChatMessageListener {

    /**
     * Called when a messaged is received by the {@link BluetoothClient}
     * Includes messages sent by this device and other devices
     *
     * @param message the message that was received
     */
    void onChatMessageReceived(ChatMessage message);
}
