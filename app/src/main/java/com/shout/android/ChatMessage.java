package com.shout.android;

import com.bridgefy.sdk.client.Message;

import java.util.HashMap;

public class ChatMessage {

    private final String content;
    private final String username;
    private final long timestamp;

    public ChatMessage(String content, String username, long timestamp) {
        this.content = content;
        this.username = username;
        this.timestamp = timestamp;
    }

    public ChatMessage(Message m){
        this.content = (String)m.getContent().get("content");
        this.username = (String)m.getContent().get("username");
        this.timestamp = m.getDateSent();
    }

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
