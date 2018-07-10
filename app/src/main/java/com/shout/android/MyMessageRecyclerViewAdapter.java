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


public class MyMessageRecyclerViewAdapter extends RecyclerView.Adapter<MyMessageRecyclerViewAdapter.ViewHolder> implements ConnectionListener, ChatMessageListener {

    private final SortedList<ChatMessage> buffer;


    MyMessageRecyclerViewAdapter() {
        BluetoothClient.getINSTANCE().registerChatMessageListener(this);
        BluetoothClient.getINSTANCE().registerConnectionListener(this);
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
                return false;
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mItem = buffer.get(position);
        holder.mContentView.setText(buffer.get(position).getContent());
        holder.mUsernameView.setText(buffer.get(position).getUsername());
        holder.mUsernameView.setTextColor(buffer.get(position).getColor());
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
        buffer.add(new ChatMessage("connected", username, timestamp, userID));
    }

    @Override
    public void deviceLost(String username, long timestamp, String userID) {
        buffer.add(new ChatMessage("disconnected", username, timestamp, userID));
    }

    @Override
    public void connectedDeviceCountChanged(int count) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mContentView;
        final TextView mUsernameView;
        ChatMessage mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = view.findViewById(R.id.content);
            mUsernameView = view.findViewById(R.id.username);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
