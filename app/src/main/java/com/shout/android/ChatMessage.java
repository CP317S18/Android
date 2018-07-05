package com.shout.android;

import com.bridgefy.sdk.client.Message;

import java.util.HashMap;

/**
 * This is the standard wrapper object for a chat message
 */
public class ChatMessage {

    private final String content;
    private final String username;
    private final long timestamp;

    ChatMessage(String content, String username, long timestamp) {
        this.content = content;
        this.username = username;
        this.timestamp = timestamp;
    }

    /**
     * Creates a {@link ChatMessage} whose content and username are set by the respective values for
     * those keys in the message content {@link HashMap}
     *
     * @param message the raw message received from the Bridgefy API
     */
    public ChatMessage(Message message) {
        this.content = (String) message.getContent().get("content");
        this.username = (String) message.getContent().get("username");
        this.timestamp = message.getDateSent();
    }


    /**
     * @return {@link HashMap} with keys content and username filled with the values from this chat message
     */
    public HashMap<String,Object> getMap(){
        HashMap<String,Object> map  = new HashMap<>();
        map.put("content",content);
        map.put("username",username);
        return map;
    }

    public String getContent() {
        return content;
    }

    public String getUsername() {
        return username;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
