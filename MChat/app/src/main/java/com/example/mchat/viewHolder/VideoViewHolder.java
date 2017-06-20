package com.example.mchat.viewHolder;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.mchat.R;
import com.example.mchat.act.VideoActivity;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMVideoMessageBody;

import java.io.File;
import java.util.HashMap;

/**
 * Created by 杜明 on 2017/6/14.
 */

public class VideoViewHolder extends RecyclerView.ViewHolder {
    private LinearLayout ll1;
    private RelativeLayout ll2;
    private ImageView image1;
    private ImageView image2;

    public VideoViewHolder(View itemView) {
        super(itemView);
        ll1 = (LinearLayout) itemView.findViewById(R.id.ll1);
        ll2 = (RelativeLayout) itemView.findViewById(R.id.ll2);
        image1 = (ImageView) itemView.findViewById(R.id.voide_image1);
        image2 = (ImageView) itemView.findViewById(R.id.video_image2);
    }

    public void setView(final EMMessage emMessage, final Context context) {
        final EMVideoMessageBody body = (EMVideoMessageBody) emMessage.getBody();
        if (EMClient.getInstance().getCurrentUser().equals(emMessage.getTo())) {
            ll1.setVisibility(View.VISIBLE);
            ll2.setVisibility(View.GONE);
            Glide.with(context)
                    .load(body.getRemoteUrl())
                    .override(200, 300)
                    .into(image1);
            image1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (new File(body.getLocalUrl()).exists()) {
                        context.startActivity(new Intent(context, VideoActivity.class).putExtra("videoPath", body.getLocalUrl()));
                    } else {
                        final String localPath = Environment.getExternalStorageDirectory() + "/" + System.currentTimeMillis() + ".mp4";
                        HashMap<String, String> hashMap = new HashMap<>();
                        if (!TextUtils.isEmpty(body.getSecret())) {
                            hashMap.put("share-secret", body.getSecret());
                            EMClient.getInstance()
                                    .chatManager()
                                    .downloadFile(body.getRemoteUrl(), localPath, hashMap, new EMCallBack() {
                                        @Override
                                        public void onSuccess() {
                                            Log.e("onSuccess", "onSuccess");
                                            body.setLocalUrl(localPath);
                                            EMClient.getInstance().chatManager().updateMessage(emMessage);
                                        }

                                        @Override
                                        public void onError(int i, String s) {
                                            Log.e("onError", s);
                                        }

                                        @Override
                                        public void onProgress(int i, String s) {
                                            Log.e("onProgress", s);
                                        }
                                    });
                        }
                    }
                }
            });
        } else {
            ll1.setVisibility(View.GONE);
            ll2.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(body.getLocalThumb())
                    .override(200, 300)
                    .into(image2);
            image2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, VideoActivity.class).putExtra("videoPath", body.getLocalUrl()));
                }
            });
        }
    }
}
