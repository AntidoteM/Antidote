package com.example.mchat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mchat.R;

import java.util.List;

/**
 * Created by 杜明 on 2017/6/20.
 */

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.ViewHolder> {
    private Context context;
    private List<String> list;

    public FriendListAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView frienditem_text;

        public ViewHolder(View itemView) {
            super(itemView);
            frienditem_text = (TextView) itemView.findViewById(R.id.frienditem_text);
        }
    }

    @Override
    public FriendListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.friend_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(FriendListAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.frienditem_text.setText(list.get(i));
        viewHolder.frienditem_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
