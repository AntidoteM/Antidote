package com.example.mchat.act;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.VideoView;

import com.example.mchat.R;

/**
 * Created by 杜明 on 2017/6/15.
 */

public class VideoActivity extends BaseActivity {
    private LinearLayout linearLayout;
    private VideoView videoView;
    private String path;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initData();
        setContentView(R.layout.activity_video);
        videoView = (VideoView) findViewById(R.id.video_res);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        videoView.setVideoPath(path);
        videoView.start();
    }

    private void initData() {
        path = getIntent().getStringExtra("videoPath");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (videoView.isPlaying()){
            videoView.stopPlayback();
        }
    }
}
