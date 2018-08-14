package com.shout.android;

import com.bridgefy.sdk.client.Message;
import com.shout.android.core.ColorManager;
import com.shout.android.core.MessageType;

import java.util.HashMap;

/**
 * This is the standard wrapper object for a chat message
 */
public class ChatMessage {

    private final String content;
    private final String username;
    private final long timestamp;
    private final String userID;
    private final MessageType type;

    ChatMessage(String content, String username, long timestamp, String userID, MessageType type) {
        this.content = content;
        this.username = username;
        this.timestamp = timestamp;
        this.userID = userID;
        this.type = type;
    }

    /**
     * Creates a {@link ChatMessage} whose content and username are set by the respective values for
     * those keys in the message content {@link HashMap}
     *
     * @param message the raw message received from the Bridgefy API
     */
    public ChatMessage(Message message, MessageType type) {
        this.content = (String) message.getContent().get("content");
        this.username = (String) message.getContent().get("username");
        this.timestamp = message.getDateSent();
        this.userID = message.getSenderId();
        this.type = type;
    }


    /**
     * @return {@link HashMap} with keys content and username filled with the values from this chat message
     */
    public HashMap<String,Object> getMap(){
        HashMap<String,Object> map  = new HashMap<>();
        map.put("content",content);
        map.put("username",username);
        map.put("type", MessageType.ChatMessage.getValue());
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

    public int getColor() {
        return ColorManager.getInstance().getColor(userID);
    }

    public MessageType getType() {
        return type;
    }
}
