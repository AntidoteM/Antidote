package com.example.mchat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.mchat.R;
import com.example.mchat.callback.RecyclerItemClick;
import com.example.mchat.javaBean.EmojiBean;

import java.util.ArrayList;

/**
 * Created by 杜明 on 2017/6/9.
 */

public class EmojiAdapter extends RecyclerView.Adapter<EmojiAdapter.MyAdapter> {
    private Context context;
    private ArrayList<EmojiBean> list;
    private RecyclerItemClick recyclerItemClick;

    public void setRecyclerItemClick(RecyclerItemClick recyclerItemClick) {
        this.recyclerItemClick = recyclerItemClick;
    }

    public EmojiAdapter(Context context, ArrayList<EmojiBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyAdapter(LayoutInflater.from(context).inflate(R.layout.emoji_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MyAdapter holder, final int position) {
        Glide.with(context)
                .load(list.get(position).getId())
                .override(100, 100)
                .into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerItemClick.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyAdapter extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public MyAdapter(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.emoji_image);
        }
    }
}
