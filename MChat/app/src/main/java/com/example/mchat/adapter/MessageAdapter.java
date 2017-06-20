package com.example.mchat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mchat.R;
import com.example.mchat.viewHolder.ImageViewViewHolder;
import com.example.mchat.viewHolder.TextViewHolder;
import com.example.mchat.viewHolder.VideoViewHolder;
import com.example.mchat.viewHolder.VoiceViewHolder;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;

import java.util.List;

/**
 * Created by 杜明 on 2017/5/9.
 */

public class MessageAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<EMMessage> list;
    private String userName;

    public MessageAdapter(Context context, List<EMMessage> list, String userName) {
        this.context = context;
        this.list = list;
        this.userName = userName;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0){
            return new TextViewHolder(LayoutInflater.from(context).inflate(R.layout.item_msg_txt,parent,false));
        }
        if (viewType == 1){
            return new ImageViewViewHolder(LayoutInflater.from(context).inflate(R.layout.item_msg_img,parent,false));
        }
        if (viewType == 2){
            return new VideoViewHolder(LayoutInflater.from(context).inflate(R.layout.item_msg_video,parent,false));
        }
        if (viewType == 3){
            return new VoiceViewHolder(LayoutInflater.from(context).inflate(R.layout.item_msg_voice,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TextViewHolder){
            ((TextViewHolder)holder).setView(list.get(position),context);
        }
        if (holder instanceof ImageViewViewHolder){
            ((ImageViewViewHolder)holder).setView(list.get(position),context);
        }
        if (holder instanceof VoiceViewHolder){
            ((VoiceViewHolder)holder).setView(list.get(position),context);
        }
        if (holder instanceof VideoViewHolder){
            ((VideoViewHolder)holder).setView(list.get(position),context);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        EMMessage emMessage = list.get(position);
        EMMessage.Type type = emMessage.getType();
        if (type == EMMessage.Type.TXT) {
            return 0;
        }
        if (type == EMMessage.Type.IMAGE) {
            return 1;
        }
        if (type == EMMessage.Type.VIDEO) {
            return 2;
        }
        if (type == EMMessage.Type.VOICE) {
            return 3;
        }
        return super.getItemViewType(position);
    }
}
