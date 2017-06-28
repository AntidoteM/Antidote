package com.example.mchat.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mchat.R;
import com.example.mchat.act.MessageActivity;
import com.example.mchat.manager.MessageManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMMessage;

import java.util.List;

/**
 * Created by 杜明 on 2017/6/20.
 */

public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.ViewHolder> {
    private Context context;
    private List<EMGroup> groupList;

    public GroupListAdapter(Context context, List<EMGroup> groupList) {
        this.context = context;
        this.groupList = groupList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView groupitem_text;

        public ViewHolder(View itemView) {
            super(itemView);
            groupitem_text = (TextView) itemView.findViewById(R.id.groupitem_text);
        }
    }

    @Override
    public GroupListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.group_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(GroupListAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.groupitem_text.setText(groupList.get(i).getGroupName());
        viewHolder.groupitem_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MessageActivity.class);
                intent.putExtra("name", groupList.get(i).getGroupName());
                intent.putExtra("type", EMMessage.ChatType.GroupChat);
                context.startActivity(intent);
                // MessageManager.getInsatance().createTxt("1234",groupList.get(i).getGroupName(), EMMessage.ChatType.GroupChat);
            }
        });
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }
}
