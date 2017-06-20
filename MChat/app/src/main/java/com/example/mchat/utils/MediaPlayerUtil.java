package com.example.mchat.utils;

import android.media.AudioManager;
import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by 杜明 on 2017/6/13.
 */

public class MediaPlayerUtil extends MediaPlayer {
    public static int StreamType = AudioManager.STREAM_MUSIC;
    private MediaPlayer mp;
    private static MediaPlayerUtil mediaPlayerUtil;

    public static synchronized MediaPlayerUtil getInstance() {
        if (mediaPlayerUtil == null) {
            mediaPlayerUtil = new MediaPlayerUtil();
        }
        return mediaPlayerUtil;
    }
    public void Play(String path){
        if (mp == null){
            mp = new MediaPlayer();
            try {
                mp.setAudioStreamType(StreamType);
                mp.setDataSource(path);
                mp.prepare();
                mp.start();
                mp.setOnCompletionListener(new OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mp.stop();
                        mp.reset();
                        mp.release();
                        mp = null;
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
