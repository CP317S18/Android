package com.shout.android.core;

import android.util.SparseArray;

public enum MessageType {
    ChatMessage(0),
    ConnectMessage(1),
    DisconnectMessage(2);

    private int value;

    private static SparseArray<MessageType> messageTypes;

    MessageType(int value) {
        this.value = value;
    }

    public static MessageType getType(int value) {
        return messageTypes.get(value, null);
    }

    public int getValue() {
        return value;
    }

    static {
        messageTypes = new SparseArray<>();
        for (MessageType type : MessageType.values()) {
            messageTypes.put(type.value, type);
        }
    }

}
