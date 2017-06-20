package com.example.mchat.viewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mchat.R;
import com.example.mchat.utils.StringUtil;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;

/**
 * Created by 杜明 on 2017/5/24.
 */

public class TextViewHolder extends RecyclerView.ViewHolder{
    private LinearLayout ll1;
    private RelativeLayout ll2;
    private TextView text1;
    private TextView text2;

    public TextViewHolder(View itemView) {
        super(itemView);
        ll1 = (LinearLayout) itemView.findViewById(R.id.ll1);
        ll2 = (RelativeLayout) itemView.findViewById(R.id.ll2);
        text1 = (TextView) itemView.findViewById(R.id.text1);
        text2 = (TextView) itemView.findViewById(R.id.text2);
    }

    public void setView(EMMessage emMessage, Context context){
        EMTextMessageBody txtMessage = (EMTextMessageBody) emMessage.getBody();
        if (EMClient.getInstance().getCurrentUser().equals(emMessage.getTo())) {
            ll2.setVisibility(View.GONE);
            ll1.setVisibility(View.VISIBLE);
            text1.setText(StringUtil.getExpressionString(context, txtMessage.getMessage()));
        } else {
            ll1.setVisibility(View.GONE);
            ll2.setVisibility(View.VISIBLE);
            text2.setText(StringUtil.getExpressionString(context, txtMessage.getMessage()));
        }
    }
}
