package com.shout.android;

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shout.android.core.BluetoothClient;
import com.shout.android.core.ChatMessageListener;


public class MyMessageRecyclerViewAdapter extends RecyclerView.Adapter<MyMessageRecyclerViewAdapter.ViewHolder> implements ChatMessageListener {

    private final SortedList<ChatMessage> buffer;


    MyMessageRecyclerViewAdapter() {
        BluetoothClient.getINSTANCE().registerChatMessageListener(this);
        buffer = new SortedList<>(ChatMessage.class, new SortedListAdapterCallback<ChatMessage>(this) {
            @Override
            public int compare(ChatMessage o1, ChatMessage o2) {
                return (int)(o1.getTimestamp()-o2.getTimestamp());
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

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = buffer.get(position);
        holder.mContentView.setText(buffer.get(position).getContent());

    }

    @Override
    public int getItemCount() {
        return buffer.size();
    }

    @Override
    public void onChatMessageRecieved(ChatMessage m) {
        buffer.add(m);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;
        public ChatMessage mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
