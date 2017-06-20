package com.example.mchat.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mchat.R;
import com.example.mchat.callback.ItemClickListener;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 杜明 on 2017/4/27.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context context;
    private ItemClickListener listItemClick;
    private ArrayList<EMConversation> list;
    private HashMap<String, String> textMap = new HashMap<>();

    public void setListItemClick(ItemClickListener listItemClick) {
        this.listItemClick = listItemClick;
    }

    public MyAdapter(ArrayList<EMConversation> list, Context context) {
        this.list = list;
        this.context = context;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout lay;
        private ImageView item_image;
        private TextView item_username;
        private TextView item_content;
        private TextView item_time;
        private TextView item_number;

        public MyViewHolder(View itemView) {
            super(itemView);
            lay = (LinearLayout) itemView.findViewById(R.id.lay);
            item_image = (ImageView) itemView.findViewById(R.id.item_image);
            item_username = (TextView) itemView.findViewById(R.id.item_username);
            item_content = (TextView) itemView.findViewById(R.id.item_content);
            item_time = (TextView) itemView.findViewById(R.id.item_time);
            item_number = (TextView) itemView.findViewById(R.id.item_number);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_chat, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        EMConversation emConversation = list.get(position);
        setName(holder, emConversation);
        setContent(holder, emConversation);
        setUnread(holder, emConversation);
        setTime(holder, emConversation);
        holder.lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listItemClick != null) {
                    listItemClick.onClick(position);
                }
            }
        });
        holder.lay.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listItemClick != null) {
                    listItemClick.onLongClick();
                }
                return true;
            }
        });
    }

    private void setTime(MyViewHolder holder, EMConversation emConversation) {
        long msgTime = emConversation.getLastMessage().getMsgTime();
        String time = DateFormat.format("yyyy-MM-dd kk:mm:ss", msgTime).toString();
        holder.item_time.setText(time);
    }

    private void setName(MyViewHolder holder, EMConversation emConversation) {
        holder.item_username.setText(emConversation.getUserName());
    }

    private void setContent(MyViewHolder holder, EMConversation emConversation) {
        EMMessage lastMessage = emConversation.getLastMessage();
        if (!TextUtils.isEmpty(textMap.get(emConversation.getUserName()))) {
            holder.item_content.setTextColor(Color.RED);
            holder.item_content.setText("[草稿] " + textMap.get(emConversation.getUserName()));
        } else {
            holder.item_content.setTextColor(Color.BLACK);
            //从最后一条消息对象中 获取该消息的消息类型
            EMMessage.Type type = lastMessage.getType();

            switch (type) {
                case TXT:
                    //获取消息体 并强转成 文本类型消息体
                    EMTextMessageBody txtMessage = (EMTextMessageBody) lastMessage.getBody();
                    //从消息体中拿到消息内容 并 设置给 控件
                    holder.item_content.setText(txtMessage.getMessage());
                    break;
                case IMAGE:
                    holder.item_content.setText("[图片]");
                    break;
                case VIDEO:
                    holder.item_content.setText("[视频]");
                    break;
                case VOICE:
                    holder.item_content.setText("[音频]");
                    break;
            }
        }
    }

    private void setUnread(MyViewHolder holder, EMConversation emConversation) {
        if (emConversation.getUnreadMsgCount() > 99) {
            holder.item_number.setText("99+");
        } else {
            holder.item_number.setText(String.valueOf(emConversation.getUnreadMsgCount()));
        }
    }

    public void setTextMap(HashMap<String, String> textMap) {
        this.textMap = textMap;
        notifyDataSetChanged();
    }

    public void notifi(ArrayList<EMConversation> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
