package com.example.mchat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.mchat.R;

import java.util.HashSet;
import java.util.List;

/**
 * Created by 杜明 on 2017/5/23.
 */

public class SelectImageRecyclerAdapter extends RecyclerView.Adapter <SelectImageRecyclerAdapter.MyViewholder>{

    private Context context;
    private List<String> list;
    private HashSet<String> paths = new HashSet<>();

    public HashSet<String> getPaths() {
        return paths;
    }

    public void setPaths(HashSet<String> paths) {
        this.paths = paths;
    }

    public SelectImageRecyclerAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    class MyViewholder extends RecyclerView.ViewHolder{
        private CheckBox image_select_cb;
        private ImageView image_select_iv;

        public MyViewholder(View itemView) {
            super(itemView);
            image_select_cb = (CheckBox) itemView.findViewById(R.id.image_select_cb);
            image_select_iv = (ImageView) itemView.findViewById(R.id.image_select_iv);
        }
    }
    @Override
    public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewholder(LayoutInflater.from(context).inflate(R.layout.image_selectd,parent,false));
    }

    @Override
    public void onBindViewHolder(MyViewholder holder, final int position) {
        Glide.with(context)
                .load(list.get(position))
                .override(500,300)
                .into(holder.image_select_iv);
        holder.image_select_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    paths.add(list.get(position));
                }else{
                    paths.remove(list.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
