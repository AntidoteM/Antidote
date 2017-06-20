package com.example.mchat.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mchat.R;
import com.example.mchat.act.MessageActivity;
import com.example.mchat.utils.MediaRecordUtil;

import java.io.File;

/**
 * Created by 杜明 on 2017/6/9.
 */

public class VoiceFragment extends MyFragment implements View.OnTouchListener {
    private ImageView btn_luyin;
    long starttime;
    long finishtime;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.record_voice, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        btn_luyin.setOnTouchListener(this);
    }

    private void initView(View view) {
        btn_luyin = (ImageView) view.findViewById(R.id.btn_luyin);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
            btn_luyin.setImageResource(R.drawable.anxia);
            starttime = System.currentTimeMillis();
            startLuyin();
        }else if (motionEvent.getAction() == MotionEvent.ACTION_UP){
            btn_luyin.setImageResource(R.drawable.songkai);
            finishtime = System.currentTimeMillis();
            finishLuyin();
        }
        return true;
    }

    private void finishLuyin() {
        File file = MediaRecordUtil.getInstance().finish();
        int time = (int) ((finishtime - starttime)/1000);
        ((MessageActivity)getActivity()).createVoice(file.getPath(),time);
    }

    private void startLuyin(){
        MediaRecordUtil.getInstance().Strat(getActivity());
    }
}
