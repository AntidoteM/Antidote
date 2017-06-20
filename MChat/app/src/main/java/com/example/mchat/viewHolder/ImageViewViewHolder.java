package com.example.mchat.viewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.mchat.R;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;

/**
 * Created by 杜明 on 2017/5/24.
 */

public class ImageViewViewHolder extends RecyclerView.ViewHolder {
    private LinearLayout ll1;
    private RelativeLayout ll2;
    private ImageView image1;
    private ImageView image2;

    public ImageViewViewHolder(View itemView) {
        super(itemView);
        ll1 = (LinearLayout) itemView.findViewById(R.id.ll1);
        ll2 = (RelativeLayout) itemView.findViewById(R.id.ll2);
        image1 = (ImageView) itemView.findViewById(R.id.image1);
        image2 = (ImageView) itemView.findViewById(R.id.image2);
    }

    public void setView(EMMessage emMessage, Context context){
        EMImageMessageBody emImg = (EMImageMessageBody) emMessage.getBody();
        if (EMClient.getInstance().getCurrentUser().equals(emMessage.getTo())) {
            ll1.setVisibility(View.VISIBLE);
            ll2.setVisibility(View.GONE);
            Glide.with(context)
                    .load(emImg.getThumbnailUrl())
                    .override(300, 200)
                    .into(image1);
        } else {
            ll1.setVisibility(View.GONE);
            ll2.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(emImg.getLocalUrl())
                    .override(300, 200)
                    .into(image2);
        }
    }
}
