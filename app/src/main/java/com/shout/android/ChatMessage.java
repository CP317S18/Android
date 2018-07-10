package com.shout.android;

import android.graphics.Color;

import com.bridgefy.sdk.client.Message;
import com.shout.android.core.BluetoothClient;
import com.shout.android.core.ColorManager;
import com.shout.android.core.MessageType;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.UUID;

/**
 * This is the standard wrapper object for a chat message
 */
public class ChatMessage {

    private final String content;
    private final String username;
    private final long timestamp;
    private final String userID;


    ChatMessage(String content, String username, long timestamp) {
        this.content = content;
        this.username = username;
        this.timestamp = timestamp;
        this.userID = BluetoothClient.getINSTANCE().getUserID();
    }

    ChatMessage(String content, String username, long timestamp, String userID) {
        this.content = content;
        this.username = username;
        this.timestamp = timestamp;
        this.userID = userID;
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
        this.userID = message.getSenderId();
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

    private int getRandomColor(UUID id) {

        byte[] bytes = UUID2Bytes(id);

        int r = Math.abs(bytes[0]);
        int g = Math.abs(bytes[1]);
        int b = Math.abs(bytes[2]);
        int color = Color.argb(255, r, g, b);
        return color;
    }

    private byte[] UUID2Bytes(UUID uuid) {

        long hi = uuid.getMostSignificantBits();
        long lo = uuid.getLeastSignificantBits();
        return ByteBuffer.allocate(16).putLong(hi).putLong(lo).array();
    }

}
