package com.shout.android.core;

import com.shout.android.ChatMessage;

public interface ChatMessageListener {

    void onChatMessageRecieved(ChatMessage m);
}
