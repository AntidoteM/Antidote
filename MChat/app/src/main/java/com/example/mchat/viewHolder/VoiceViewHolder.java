package com.example.mchat.viewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mchat.R;
import com.example.mchat.utils.MediaPlayerUtil;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMVoiceMessageBody;

/**
 * Created by 杜明 on 2017/6/9.
 */

public class VoiceViewHolder extends RecyclerView.ViewHolder {
    private LinearLayout ll1;
    private RelativeLayout ll2;
    private TextView left_text;
    private TextView right_text;

    public VoiceViewHolder(View itemView) {
        super(itemView);
        ll1 = (LinearLayout) itemView.findViewById(R.id.ll1);
        ll2 = (RelativeLayout) itemView.findViewById(R.id.ll2);
        left_text = (TextView) itemView.findViewById(R.id.left_text);
        right_text = (TextView) itemView.findViewById(R.id.right_text);
    }

    public void setView(EMMessage emMessage, Context context) {
        final EMVoiceMessageBody voiceMessage = (EMVoiceMessageBody) emMessage.getBody();
        float time = voiceMessage.getLength();
        if (EMClient.getInstance().getCurrentUser().equals(emMessage.getTo())) {
            ll2.setVisibility(View.GONE);
            ll1.setVisibility(View.VISIBLE);
            left_text.setText("\"" + time + "\"");
            left_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (voiceMessage.downloadStatus().equals(EMVoiceMessageBody.EMDownloadStatus.SUCCESSED)) {
                        MediaPlayerUtil.getInstance().Play(voiceMessage.getLocalUrl());
                    }
                }
            });
        } else {
            ll1.setVisibility(View.GONE);
            ll2.setVisibility(View.VISIBLE);
            right_text.setText("\"" + time + "\"");
            right_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (voiceMessage.downloadStatus().equals(EMVoiceMessageBody.EMDownloadStatus.SUCCESSED)) {
                        MediaPlayerUtil.getInstance().Play(voiceMessage.getLocalUrl());
                    }
                }
            });
        }
    }
}
