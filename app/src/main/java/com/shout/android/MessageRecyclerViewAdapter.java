package com.shout.android;

import android.support.annotation.NonNull;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shout.android.core.BluetoothClient;
import com.shout.android.core.ChatMessageListener;
import com.shout.android.core.ConnectionListener;
import com.shout.android.core.MessageType;


public class MessageRecyclerViewAdapter extends RecyclerView.Adapter<MessageRecyclerViewAdapter.MessageViewHolder> implements ConnectionListener, ChatMessageListener {

    private final SortedList<ChatMessage> buffer;


    MessageRecyclerViewAdapter() {
        BluetoothClient.getInstance().registerChatMessageListener(this);
        BluetoothClient.getInstance().registerConnectionListener(this);
        buffer = new SortedList<>(ChatMessage.class, new SortedListAdapterCallback<ChatMessage>(this) {
            @Override
            public int compare(ChatMessage o1, ChatMessage o2) {
                return (int) (o2.getTimestamp() - o1.getTimestamp());
            }

            @Override
            public boolean areContentsTheSame(ChatMessage oldItem, ChatMessage newItem) {
                return false;
            }

            @Override
            public boolean areItemsTheSame(ChatMessage item1, ChatMessage item2) {
                return item1.equals(item2);
            }
        });
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MessageType type = MessageType.getType(viewType);
        View view;
        MessageViewHolder viewHolder = null;
        switch (type) {
            case ChatMessage:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fragment_message, parent, false);
                viewHolder = new ChatMessageViewHolder(view);
                break;
            case ConnectMessage:
            case DisconnectMessage:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fragment_message_system, parent, false);
                viewHolder = new SystemMessageViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        holder.bind(buffer.get(position));
    }

    @Override
    public int getItemCount() {
        return buffer.size();
    }

    @Override
    public void onChatMessageReceived(ChatMessage m) {
        buffer.add(m);
    }

    @Override
    public void deviceConnected(String username, long timestamp, String userID) {
        buffer.add(new ChatMessage("connected", username, timestamp, userID, MessageType.ConnectMessage));
    }

    @Override
    public void deviceLost(String username, long timestamp, String userID) {
        buffer.add(new ChatMessage("disconnected", username, timestamp, userID, MessageType.DisconnectMessage));
    }

    @Override
    public void connectedDeviceCountChanged(int count) {

    }

    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous

        return buffer.get(position).getType().getValue();
    }

    abstract class MessageViewHolder extends RecyclerView.ViewHolder {

        final View containerView;

        MessageViewHolder(View itemView) {
            super(itemView);
            containerView = itemView;
        }

        abstract void bind(ChatMessage message);
    }


    public class SystemMessageViewHolder extends MessageViewHolder {

        final TextView contentView;

        SystemMessageViewHolder(View view) {
            super(view);
            contentView = view.findViewById(R.id.systemContent);
        }

        @Override
        void bind(ChatMessage message) {
            if (message != null) {
                contentView.setText(message.getUsername() + "has" + message.getContent());
            }
        }
    }

    public class ChatMessageViewHolder extends MessageViewHolder {
        final TextView contentView;
        final TextView usernameView;

        ChatMessageViewHolder(View view) {
            super(view);
            contentView = view.findViewById(R.id.content);
            usernameView = view.findViewById(R.id.username);
        }

        void bind(ChatMessage message) {
            if (message != null) {
                contentView.setText(message.getContent());
                usernameView.setText(message.getUsername());
                usernameView.setTextColor(message.getColor());
            }
        }


        @Override
        public String toString() {
            return super.toString() + " '" + contentView.getText() + "'";
        }
    }
}
